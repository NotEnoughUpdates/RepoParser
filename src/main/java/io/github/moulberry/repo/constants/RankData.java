package io.github.moulberry.repo.constants;

import lombok.Value;
import org.jetbrains.annotations.Nullable;

@Value
public class RankData {
    /**
     * A one character color code, as used by minecraft's {@code §}.
     */
    String color;
    /**
     * The prefix tag, typically enclosed in brackets.
     */
    String tag;
    /**
     * Extra pluses for the prefix tag, in a user speified color.
     */
    @Nullable
    String plus;
}
