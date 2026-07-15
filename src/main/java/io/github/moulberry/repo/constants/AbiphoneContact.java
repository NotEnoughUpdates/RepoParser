package io.github.moulberry.repo.constants;

import java.util.List;

import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class AbiphoneContact {
    @Getter
    @Nullable List<String> callNames;

    @Getter
    @SerializedName("requirement")
    List<String> requirements;

    @Getter
    @Nullable String island;

    @Getter
    @Nullable Integer x;

    @Getter
    @Nullable Integer y;

    @Getter
    @Nullable Integer z;
}
