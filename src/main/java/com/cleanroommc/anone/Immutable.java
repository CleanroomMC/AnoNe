package com.cleanroommc.anone;

import java.lang.annotation.*;

/**
 * Marks the type as immutable.
 * This would mean that the annotated type strictly does not allow mutation to be applied.
 * This annotation doesn't ensure complete immutability when Reflection or any bytecode manipulation is being used.
 * <p>
 * It has stronger semantics than {@link Unmodifiable}.
 *
 * @see Singleton
 * @see Unmodifiable
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.CLASS)
public @interface Immutable { }
