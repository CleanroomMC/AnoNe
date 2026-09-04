/*
 * Copyright (c) 2022-2026 CleanroomMC contributors
 * SPDX-License-Identifier: MIT
 */

package com.cleanroommc.anone.api;

import java.lang.annotation.*;

/**
 * Marks the API as internal to its own library or module.
 * <p>
 * Internal APIs are implementation details: they are not covered by any compatibility
 * guarantees and may change or disappear at any time. External code must not reference them.
 * <p>
 * When applied to a type, all of its members are considered internal as well.
 * When applied to a package (via {@code package-info.java}), everything in that package
 * is considered internal.
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PACKAGE })
@Retention(RetentionPolicy.CLASS)
public @interface Internal { }
