package zone.rong.anone;

import zone.rong.anone.value.SingletonInstance;

import java.lang.annotation.*;

/**
 * Marks the type as a Singleton.
 *
 * This would mean that the annotated class is intended to have only one instance of itself in memory during runtime.
 *
 * @see SingletonInstance
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Singleton { }
