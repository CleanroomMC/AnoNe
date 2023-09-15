package com.cleanroommc.anone.reveal;

import java.lang.annotation.*;

/**
 * Marks the method or constructor as invoked via generated code.
 * This would mean the method or constructor isn't unused but called at runtime
 * via code that was generated after compile, most possibly at runtime.
 * However, this annotation doesn't imply that the said method or constructor cannot be invoked normally.
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.METHOD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.CLASS)
public @interface GeneratedInvocation { }
