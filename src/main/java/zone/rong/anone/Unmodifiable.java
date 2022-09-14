package zone.rong.anone;

import java.lang.annotation.*;

/**
 * Marks the type (most notably {@link java.util.Collection} or {@link java.util.Map}) as having an unmodifiable view.
 *
 * This would mean that the annotated type would error or produce no effect
 * when any mutator methods such as {@link java.util.Collection#add(Object)} is called.
 *
 * This annotation doesn't ensure the complete immutability as the object may still be able to be mutated in a different context.
 *
 * @see Immutable
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.CLASS)
public @interface Unmodifiable { }
