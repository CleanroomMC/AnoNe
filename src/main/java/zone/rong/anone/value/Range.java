package zone.rong.anone.value;

import java.lang.annotation.*;

/**
 * Marks the type as having a defined allowed range of numbers.
 * Applies to any Number primitives.
 *
 * @see Byte
 * @see Character
 * @see Short
 * @see Integer
 * @see Long
 *
 * @since 1.0.0
 */
@Documented
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.CLASS)
public @interface Range {

    /**
     * @return minimum value allowed (inclusive)
     */
    long from();

    /**
     * @return maximum value allowed (inclusive)
     */
    long to();

}
