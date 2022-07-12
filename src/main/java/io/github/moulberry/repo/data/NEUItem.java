package io.github.moulberry.repo.data;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public class NEUItem {
    @SerializedName("itemid")
    String minecraftItemId;
    @SerializedName("displayname")
    String displayName;
    String nbttag;
    int damage;
    List<String> lore;
    @SerializedName("internalname")
    String skyblockItemId;
    String crafttext;
    String clickcommand;
    String modver;
    String infoType;
    List<String> info;

}
