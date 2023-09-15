package com.cleanroommc.anone.reveal;

import java.lang.annotation.*;

/**
 * Marks the method or constructor as invoked via Reflection: {@link java.lang.reflect}.
 * This would mean the method or constructor isn't unused but called reflectively.
 * However, this annotation doesn't imply that the said method or constructor cannot be invoked normally.
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.CLASS)
public @interface ReflectiveInvocation { }
