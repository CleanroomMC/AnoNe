/*
 * Copyright (c) 2022-2026 CleanroomMC contributors
 * SPDX-License-Identifier: MIT
 */

package com.cleanroommc.anone.api;

import java.lang.annotation.*;

/**
 * Marks the API as visible only for testing.
 * <p>
 * This is typically used when production code widens the visibility of a member
 * (e.g. from {@code private} to package-private or {@code public}) solely so tests
 * can access it.
 * <p>
 * A caller counts as test code when its top-level class name ends with {@code Test},
 * {@code Tests} or {@code TestCase}, when any enclosing class or method is itself
 * annotated with {@code @VisibleForTesting}, or when its package name contains a
 * {@code test}, {@code tests} or {@code testing} segment.
 * <p>
 * When applied to a type, all of its members are considered visible-for-testing as well.
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR })
@Retention(RetentionPolicy.CLASS)
public @interface VisibleForTesting { }
