/*
 * Copyright (c) 2022-2026 CleanroomMC contributors
 * SPDX-License-Identifier: MIT
 */

package com.cleanroommc.anone.canon;

/**
 * @see MustCallAt
 * @see MustNotCallAt
 *
 * @since 1.0.0
 */
public enum CallScope {

    /**
     * Applies to the directly overriding implementation.
     */
    DIRECT_OVERRIDERS,

    /**
     * Applies to overriding implementation, including all the inheritance hierarchies.
     */
    TRANSITIVE_OVERRIDERS,
}
