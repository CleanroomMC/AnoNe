package com.cleanroommc.anone.canon;

import java.lang.annotation.*;

/**
 * It specifies which callers are forbidden from calling the annotated method.
 * <p>
 * With the default parameters, this annotation applies to all the overriders of
 * the annotated method, including all the inheritance hierarchies,
 * which behaves similarly to <code>MustNotCallSuper</code>.
 *
 * <hr>
 *
 * However, this is different from a <code>MustNotCallSuper</code> annotation,
 * and it's capable of applying to arbitrary method inheritance hierarchies.
 * <p><br>
 * <b>Example</b>
 * <pre>{@code
 * class A
 * {
 *     @MustNotCallAt(position = CallPosition.ANYWHERE, scopeRoot = { B.class })
 *     public static final void internalMethod() { }
 * }
 *
 * abstract class B
 * {
 *     abstract void method1();
 *     abstract void method2();
 * }
 *
 * class C extends B
 * {
 *     @Override
 *     void method1()
 *     {
 *         // must not call A#internalMethod
 *     }
 *
 *     @Override
 *     void method2()
 *     {
 *         // must not call A#internalMethod
 *     }
 * }
 * }
 * </pre>
 *
 * @see MustCallAt
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface MustNotCallAt {

    /**
     * Forbidden call position.
     *
     * @see CallPosition
     */
    CallPosition position() default CallPosition.HEAD;

    /**
     * Scope in which this contract is enforced.
     * This is applied to every class specified by {@link #scopeRoot()}.
     * <p>
     * When it applies to {@link Super}, the target remains only on the annotated method.
     * As a result, only overriders of the annotated method are required to follow the contract of {@link MustNotCallAt}.
     * <p>
     * When it applies to other classes, the target is extended to their inheritors.
     * All overrides of methods declared by the specified class become targets,
     * since there's no option provided to specify a method signature.
     * As a result, those overrides are required to follow the contract of {@link MustNotCallAt}.
     *
     * @see #scopeRoot()
     * @see CallScope
     */
    CallScope scope() default CallScope.TRANSITIVE_OVERRIDERS;

    /**
     * Specifies the classes from which overriders start being counted.
     *
     * @see #scope()
     * @see Super
     */
    Class<?>[] scopeRoot() default { Super.class };
}
