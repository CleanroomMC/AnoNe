package zone.rong.anone.value;

import zone.rong.anone.Singleton;

import java.lang.annotation.*;

/**
 * Marks the field to be a SingletonInstance, or a method to return a SingletonInstance
 *
 * This would mean that the annotated field holds a value that is a SingletonInstance for a Singleton.
 * When a method is annotated with this, it means the method is a getter for the SingletonInstance.
 *
 * @see Singleton
 *
 * @since 1.0.0
 */
@Documented
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface SingletonInstance { }
