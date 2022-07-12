package io.github.moulberry.repo.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Type;

@Data
@AllArgsConstructor
public class Coordinate {
    int x, y, z;

    public static class CoordinateSerializer implements JsonDeserializer<Coordinate> {
        @Override
        public Coordinate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String asString = json.getAsString();
            String[] split = asString.split(",");
            if (split.length != 3)
                throw new JsonParseException("Coordinate must be a string consisting of 3 numbers seperated by , but is `" + asString + "`");
            return new Coordinate(
                    Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]),
                    Integer.parseInt(split[2])
            );
        }
    }
}
