package com.cleanroommc.anone.value;

import java.lang.annotation.*;

/**
 * When applied to a class, it indicates that all methods declared in the class must have
 * their return values checked by callers.
 * <p>
 * When applied to a method, it indicates that the method's return value must
 * be checked by callers.
 * <p>
 * Method-level annotations take precedence over class-level annotations.
 * For example, method-level {@link CheckReturnValue} overrides class-level {@link CanIgnoreReturnValue}.
 * <p>
 * This annotation targets callers rather than implementations.
 *
 * @see CanIgnoreReturnValue
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface CheckReturnValue { }
