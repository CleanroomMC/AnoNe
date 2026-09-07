/*
 * Copyright (c) 2022-2026 CleanroomMC contributors
 * SPDX-License-Identifier: MIT
 */

package com.cleanroommc.anone.processor;

import com.cleanroommc.anone.canon.InvokeOnly;
import com.cleanroommc.anone.canon.OverrideOnly;
import com.cleanroommc.anone.lifecycle.MustNotClose;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("*")
public class AnoNeProcessor extends AbstractProcessor {

    private static final String INVOKE_ONLY_MESSAGE = "overrides method from %s::%s annotated with @InvokeOnly. This method is intended to be invoked only and not overridden.";
    private static final String OVERRIDE_ONLY_MESSAGE = "invoked %s::%s annotated with @OverrideOnly. This method is intended to be overridden only and not invoked.";
    private static final String MUST_NOT_CLOSE_MESSAGE = "closed %s annotated with @MustNotClose. This resource is owned elsewhere and must not be closed here.";

    private Trees trees;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.trees = Trees.instance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }
        for (Element root : roundEnv.getRootElements()) {
            if (root instanceof TypeElement) {
                scanForOverrides((TypeElement) root);
                TreePath path = this.trees.getPath(root);
                if (path != null) {
                    new InvocationScanner().scan(path, null);
                }
            }
        }
        return false;
    }

    private void scanForOverrides(TypeElement typeElement) {
        for (Element enclosed : typeElement.getEnclosedElements()) {
            if (enclosed instanceof ExecutableElement) {
                this.checkOverride((ExecutableElement) enclosed);
            }
        }
    }

    private void checkOverride(ExecutableElement method) {
        TypeElement enclosing = (TypeElement) method.getEnclosingElement();
        ExecutableElement overridden = findOverriddenMethod(method, enclosing);
        if (overridden != null && overridden.getAnnotation(InvokeOnly.class) != null) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format(INVOKE_ONLY_MESSAGE, overridden.getEnclosingElement(), overridden.getSimpleName()),
                    method);
        }
    }

    private ExecutableElement findOverriddenMethod(ExecutableElement method, TypeElement type) {
        TypeMirror superclass = type.getSuperclass();
        if (superclass.getKind() != TypeKind.NONE) {
            ExecutableElement found = findOverriddenMethod(method, superclass);
            if (found != null) {
                return found;
            }
        }
        for (TypeMirror iface : type.getInterfaces()) {
            ExecutableElement found = findOverriddenMethod(method, iface);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    private ExecutableElement findOverriddenMethod(ExecutableElement method, TypeMirror type) {
        Element element = this.processingEnv.getTypeUtils().asElement(type);
        if (element instanceof TypeElement) {
            ExecutableElement found = findMethodIn((TypeElement) element, method);
            if (found != null) {
                return found;
            }
            return findOverriddenMethod(method, (TypeElement) element);
        }
        return null;
    }

    private ExecutableElement findMethodIn(TypeElement type, ExecutableElement signature) {
        for (Element element : type.getEnclosedElements()) {
            if (element instanceof ExecutableElement) {
                ExecutableElement executable = (ExecutableElement) element;
                if (this.processingEnv.getElementUtils().overrides(signature, executable, (TypeElement) signature.getEnclosingElement())) {
                    return executable;
                }
            }
        }
        return null;
    }

    private class InvocationScanner extends TreePathScanner<Void, Void> {

        @Override
        public Void visitMethodInvocation(MethodInvocationTree node, Void unused) {
            TreePath currentPath = this.getCurrentPath();
            Element element = trees.getElement(currentPath);
            if (element instanceof ExecutableElement) {
                ExecutableElement method = (ExecutableElement) element;
                if (this.isClose(method) && node.getMethodSelect() instanceof MemberSelectTree) {
                    this.checkNotClosed(((MemberSelectTree) node.getMethodSelect()).getExpression());
                }
                OverrideOnly usage = method.getAnnotation(OverrideOnly.class);
                if (usage != null) {
                    if (!node.getMethodSelect().toString().startsWith("super.")) {
                        TreePath path = currentPath;
                        while (path != null && !(path.getLeaf() instanceof MethodTree)) {
                            path = path.getParentPath();
                        }
                        Element enclosing = null;
                        if (path != null) {
                            enclosing = trees.getElement(path);
                        }
                        if (enclosing != null) {
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                    String.format(OVERRIDE_ONLY_MESSAGE, method.getEnclosingElement(), method),
                                    enclosing);
                        }
                    }
                }
            }
            return super.visitMethodInvocation(node, unused);
        }

        @Override
        public Void visitTry(TryTree node, Void unused) {
            for (Tree resource : node.getResources()) {
                Tree value = resource instanceof VariableTree ? ((VariableTree) resource).getInitializer() : resource;
                if (value != null) {
                    this.checkNotClosed(value);
                }
            }
            return super.visitTry(node, unused);
        }

        private boolean isClose(ExecutableElement method) {
            return method.getParameters().isEmpty() && method.getSimpleName().contentEquals("close");
        }

        private void checkNotClosed(Tree value) {
            TreePath path = TreePath.getPath(this.getCurrentPath(), value);
            if (path == null) {
                return;
            }
            Element element = trees.getElement(path);
            if (element != null && element.getAnnotation(MustNotClose.class) != null) {
                trees.printMessage(Diagnostic.Kind.ERROR, String.format(MUST_NOT_CLOSE_MESSAGE, element),
                        path.getLeaf(), path.getCompilationUnit());
            }
        }

    }
}
