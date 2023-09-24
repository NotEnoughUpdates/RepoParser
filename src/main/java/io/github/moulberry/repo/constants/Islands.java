package io.github.moulberry.repo.constants;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class Islands {
    /**
     * User friendly names from {@code /locraw} names
     */
    @SerializedName("area_names")
    @Unmodifiable Map<@NotNull String, @NotNull String> areaNames = new HashMap<>();

    /**
     * List of portals
     */
    @Unmodifiable @NotNull List<@NotNull Teleporter> teleporters = new ArrayList<>();

    /**
     * List of /warp exit points
     */
    @SerializedName("island_warps")
    @Unmodifiable @NotNull List<@NotNull Warp> warps = new ArrayList<>();

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Warp {
        /**
         * Name of this warp in `/warp`
         */
        @NotNull String warp;
        /**
         * Alia
         */
        @NotNull @Unmodifiable List<@NotNull String> aliases = new ArrayList<>();
        /**
         * The target island as a {@code /locraw} name
         */
        @NotNull String mode;
        /**
         * Coordinates of the target location
         */
        float x, y, z;
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Teleporter {
        /**
         * Coordinates of the teleporter on the entry side
         */
        float x, y, z;
        /**
         * {@code /locraw} name of the entry side of the teleporter
         */
        @NotNull String from;
        /**
         * {@code /locraw} name of the exit side of the teleporter
         */
        @NotNull String to;
    }
}

