package com.cleanroommc.anone.reveal;

import java.lang.annotation.*;

/**
 * Marks the field as accessed via generated code.
 * This would mean the field isn't unused but called at runtime via code that was generated after compile, most possibly at runtime.
 * However, this annotation doesn't imply that the said field cannot be accessed normally.
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface GeneratedAccess { }
