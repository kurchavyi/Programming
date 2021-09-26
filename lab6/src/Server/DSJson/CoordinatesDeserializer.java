package Server.DSJson;

import com.google.gson.*;
import Server.Organization.Coordinates;
import java.lang.reflect.Type;

/**
 *  class for deserialization from json to Coordinates.
 */
public class CoordinatesDeserializer implements JsonDeserializer<Coordinates>{
    /**
     *  deserialize from json to Coordinates.
     * @return Coordinates.
     */
    @Override
    public Coordinates deserialize(JsonElement jsonElement, Type type,
                                   JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        Integer x = null;
        try {
            x = jsonObject.get("x").getAsInt();
        } catch (NumberFormatException e) {
            x = null;
        }
        Double y = null;
        try {
            y = jsonObject.get("y").getAsDouble();
        } catch (NumberFormatException e) {
            y = null;
        }
        return new Coordinates(x, y);
    }
}