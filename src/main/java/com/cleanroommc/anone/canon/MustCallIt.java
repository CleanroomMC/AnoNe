package com.cleanroommc.anone.canon;

import java.lang.annotation.*;

/**
 * It specifies which methods are required to call the annotated method.
 * <p>
 * With the default parameters, this annotation applies to all the overriders of
 * the annotated method, including all the inheritance hierarchies,
 * which behaves similarly to <code>Must Call Super</code>.
 *
 * <hr>
 *
 * However, this is different from a <code>Must Call Super</code> annotation,
 * and it's capable of applying to arbitrary method inheritance hierarchies.
 * <p><br>
 * <b>Example</b>
 * <pre>{@code
 * class A
 * {
 *     @MustCallIt(scopeRoot = { B.class })
 *     public static final void checkPreconditions(B b) { }
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
 *         A.checkPreconditions(this); // must call at CallPosition.HEAD
 *     }
 *
 *     @Override
 *     void method2()
 *     {
 *         A.checkPreconditions(this); // must call at CallPosition.HEAD
 *     }
 * }
 * }
 * </pre>
 *
 * @see MustNotCallIt
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface MustCallIt {

    /**
     * Required call position.
     *
     * @see CallPosition
     */
    CallPosition position() default CallPosition.HEAD;

    /**
     * Scope in which this contract is enforced.
     * This is applied to every {@link #scopeRoot()} class.
     * <p>
     * When it applies to {@link Super}, the target remains only on the annotated method.
     * As a result, only the overriders of the annotated method are required to follow the contract of {@link MustCallIt}.
     * <p>
     * When it applies to other classes, the target extends to all its declared methods since
     * there's no option provided to specify a method signature.
     * As a result, overriders of all the declared methods are required to follow the contract of {@link MustCallIt}.
     *
     * @see #scopeRoot()
     * @see CallScope
     */
    CallScope scope() default CallScope.TRANSITIVE_OVERRIDERS;

    /**
     * It specifies where to start counting overriders.
     *
     * @see #scope()
     * @see Super
     */
    Class<?>[] scopeRoot() default { Super.class };
}
