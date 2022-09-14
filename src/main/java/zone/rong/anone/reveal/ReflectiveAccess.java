package zone.rong.anone.reveal;

import java.lang.annotation.*;

/**
 * Marks the field as accessed via Reflection: {@link java.lang.reflect}
 *
 * This would mean the field isn't unused but accessed reflectively.
 *
 * However, this annotation doesn't imply that the said field cannot be accessed normally.
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ReflectiveAccess { }
