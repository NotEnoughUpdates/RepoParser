package io.github.moulberry.repo.util;

import java.lang.annotation.*;

/**
 * Indicates that this string is a neu internalname / neu id.
 * When applied to a method, indicates the return type is a neu id.
 * When applied to a parameter, field, or local variable indicated that the variable type is a neu id.
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.CLASS)
public @interface NEUId {
}
