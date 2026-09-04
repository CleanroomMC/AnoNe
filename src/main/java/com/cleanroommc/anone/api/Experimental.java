/*
 * Copyright (c) 2022-2026 CleanroomMC contributors
 * SPDX-License-Identifier: MIT
 */

package com.cleanroommc.anone.api;

import java.lang.annotation.*;

/**
 * Marks the API as experimental.
 * <p>
 * Experimental APIs are unstable by definition: their signatures, semantics, or even
 * their existence may change without prior notice. Callers should be prepared for breakage
 * on update and should not expose experimental types in their own stable signatures
 * without careful consideration.
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PACKAGE })
@Retention(RetentionPolicy.CLASS)
public @interface Experimental { }
