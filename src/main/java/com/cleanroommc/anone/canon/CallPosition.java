package com.cleanroommc.anone.canon;

/**
 * @see MustCallAt
 * @see MustNotCallAt
 *
 * @since 1.0.0
 */
public enum CallPosition {

    /**
     * The target call must be the exact first executable statement.
     * <p><b>Good Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     targetCall(); // ✅
     *     randomCall();
     * }
     * </code></pre>
     * <p><b>Bad Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     if (condition)
     *     {
     *         targetCall(); // ❌
     *     }
     * }
     * </code></pre>
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *         targetCall(); // ❌
     *     }
     * }
     * </code></pre>
     */
    HEAD,

    /**
     * The target call must be wrapped inside a <code>finally { ... }</code> block, and the
     * function must only contain a single <code>finally { ... }</code> block.
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
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *         targetCall(); // ✅
     *         randomCall();
     *     }
     * }
     * </code></pre>
     * <p><b>Bad Examples:</b></p>
     * <pre><code>
     * void func()
     * {
     *     randomCall();
     *     targetCall(); // ❌
     * }
     * </code></pre>
     * <pre><code>
     * void func()
     * {
     *     try
     *     {
     *
     *     }
     *     finally
     *     {
     *
     *     }
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
     */
    IN_FINALLY,
}
