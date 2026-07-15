package io.github.moulberry.repo.constants;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import lombok.Value;

@Value
public class ResourcePack {
    @SerializedName("item_models")
    ItemModels itemModels;

    @Value
    public static class ItemModels {
        List<String> removed;

        Map<String, String> renamed;
    }
}
