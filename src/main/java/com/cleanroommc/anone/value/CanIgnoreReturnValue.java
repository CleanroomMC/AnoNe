package com.cleanroommc.anone.value;

import java.lang.annotation.*;

/**
 * When applied to a class, it indicates that all methods declared in the class can have
 * their return values ignored by callers.
 * <p>
 * When applied to a method, it indicates that the method's return value can
 * be ignored by callers.
 * <p>
 * Method-level annotations take precedence over class-level annotations.
 * For example, method-level {@link CanIgnoreReturnValue} overrides class-level {@link CheckReturnValue}.
 * <p>
 * This annotation targets callers rather than implementations.
 *
 * @see CheckReturnValue
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface CanIgnoreReturnValue { }
