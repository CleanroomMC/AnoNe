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
public enum CallPosition {

    /**
     * The target call must be the strict first executable statement of the function.
     * <p>
     * A leading <code>try</code> block is also allowed. In that case, the target call must be
     * the strict first executable statement inside the <code>try</code> block.
     * <p>
     * The call must be a direct executable statement. Calls inside assignments, conditions,
     * synchronized blocks, try-with-resources declarations, lazy expressions, or other nested
     * expressions or control-flow structures do not match.
     *
     * <p><b>Good Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     targetCall(); // ✅
     *     randomCall();
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *         targetCall(); // ✅
     *         randomCall();
     *     }
     *     finally
     *     {
     *
     *     }
     * }
     * </code></pre>
     *
     * <p><b>Bad Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     value = targetCall(); // ❌
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     if (condition)
     *     {
     *         targetCall(); // ❌
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     if (targetCall()) // ❌
     *     {
     *
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     synchronized (lock)
     *     {
     *         targetCall(); // ❌
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     synchronized (targetCall()) // ❌
     *     {
     *
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     try (Resource resource = targetCall()) // ❌
     *     {
     *
     *     }
     * }
     * </code></pre>
     */
    HEAD,

    /**
     * The target call must be a strict executable statement inside a
     * <code>finally { ... }</code> block.
     * <p>
     * Any <code>finally</code> block matches, including nested ones.
     * The call does not need to appear at any specific position inside the block.
     * <p>
     * The call must be a direct executable statement. Calls inside conditions, assignments,
     * lazy expressions, or other nested expressions or control-flow structures do not match.
     *
     * <p><b>Good Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *         randomCall();
     *         targetCall(); // ✅
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *         try
     *         {
     *
     *         }
     *         finally
     *         {
     *             targetCall(); // ✅
     *         }
     *     }
     * }
     * </code></pre>
     *
     * <p><b>Bad Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     targetCall(); // ❌
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *         if (condition)
     *         {
     *             targetCall(); // ❌
     *         }
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *         if (targetCall()) // ❌
     *         {
     *
     *         }
     *     }
     * }
     * </code></pre>
     */
    IN_FINALLY,

    /**
     * The target call must be a strict executable statement inside the outermost
     * <code>finally { ... }</code> block of the function.
     * <p>
     * The corresponding <code>try</code> statement must strictly cover the entire function body.
     * Catch blocks are allowed, but no executable statement may exist outside that
     * <code>try</code>/<code>catch</code>/<code>finally</code> statement.
     * <p>
     * Nested <code>finally</code> blocks inside <code>finally</code> are not allowed.
     * <p>
     * The call must be a direct executable statement inside the outermost
     * <code>finally</code> block. Calls inside conditions, assignments, lazy expressions,
     * or other nested expressions or control-flow structures do not match.
     *
     * <p><b>Good Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *         randomCall();
     *     }
     *     finally
     *     {
     *         targetCall(); // ✅
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *         randomCall();
     *     }
     *     catch (Exception e)
     *     {
     *         handle(e);
     *     }
     *     finally
     *     {
     *         randomCall();
     *         targetCall(); // ✅
     *     }
     * }
     * </code></pre>
     *
     * <p><b>Bad Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     randomCall();
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *         targetCall(); // ❌
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *         try
     *         {
     *
     *         }
     *         finally
     *         {
     *             targetCall(); // ❌
     *         }
     *     }
     * }
     * </code></pre>
     *
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *         if (condition)
     *         {
     *             targetCall(); // ❌
     *         }
     *     }
     * }
     * </code></pre>
     */
    OUTERMOST_FINALLY,

    /**
     * The target only needs to be referenced somewhere in the function.
     * Its position and surrounding expression or control-flow structure do not matter.
     */
    ANYWHERE
}
