/*
 * Copyright (c) 2022-2026 CleanroomMC contributors
 * SPDX-License-Identifier: MIT
 */

package com.cleanroommc.anone.lifecycle;

import java.lang.annotation.*;

/**
 * Mark any resource where the annotated element does not hand ownership of.
 * Whoever receives the resource must not close it.
 *
 * <p>
 * On a method it applies to the returned resource, on a parameter it applies to
 * the argument the caller hands over, and on a field it applies to the field's value.
 *
 * <hr>
 *
 * <b>Example</b>
 * <pre>{@code
 * class Assets {
 *     private final InputStream shared = openShared();
 *
 *     @MustNotClose
 *     public InputStream stream() { return this.shared; }
 * }
 *
 * class Loader {
 *     void bad(Assets assets) throws IOException {
 *         assets.stream().close();                    // Forbidden, Assets owns the stream
 *         try (InputStream in = assets.stream()) { }  // Forbidden, the same close in disguise
 *     }
 *
 *     void good(Assets assets) throws IOException {
 *         assets.stream().read(); // Fine, Assets closes it when it is done
 *     }
 * }
 * }
 * </pre>
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.CLASS)
public @interface MustNotClose { }
