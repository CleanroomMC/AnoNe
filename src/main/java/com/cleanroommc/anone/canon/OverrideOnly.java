package com.cleanroommc.anone.canon;

import java.lang.annotation.*;

/**
 * It marks that the method is intended to be overridden, not called.
 * It targets potential callers.
 * <p>
 * See {@link InvokeOnly} for the opposite side.
 *
 * @see InvokeOnly
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface OverrideOnly { }
