package io.github.moulberry.repo.util;

import java.lang.annotation.*;

/**
 * Indicates that this string is a pet id. Unlike {@link NEUId}, this pet id
 * does not include the {@code ;RARITY} postfix. So an {@link PetId} {@link String}
 * would be {@code "BEE"}, while a {@link NEUId} {@link String} would be {@code "BEE;2"}.
 * When applied to a method, indicates the return type is a pet id.
 * When applied to a parameter, field, or local variable indicated that the variable type is a pet id.
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.CLASS)
public @interface PetId {
}
