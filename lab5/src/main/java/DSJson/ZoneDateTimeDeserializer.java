package DSJson;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRulesException;

/**
 *  class for deserialization from json to ZoneDateTime.
 */
public class ZoneDateTimeDeserializer implements JsonDeserializer<ZonedDateTime>{
    /**
     *  deserialize from json to ZoneDateTime.
     * @return ZoneDateTime.
     */
    @Override
    public ZonedDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject datetime = jsonObject.get("dateTime").getAsJsonObject();
        JsonObject date = datetime.get("date").getAsJsonObject();
        int year = -1;
        try {
            year = date.get("year").getAsInt();
        } catch (NumberFormatException e) {
            return null;
        }
        int month = -1;
        try {
            month = date.get("month").getAsInt();
        } catch (NumberFormatException e) {
            return null;
        }
        int day = -1;
        try {
            day = date.get("day").getAsInt();
        } catch (NumberFormatException e) {
            return null;
        }
        JsonObject time = datetime.get("time").getAsJsonObject();
        int hour = -1;
        try {
            hour = time.get("hour").getAsInt();
        } catch (NumberFormatException e) {
            return null;
        }
        int minute = -1;
        try {
            minute = time.get("minute").getAsInt();
        } catch (NumberFormatException e) {
            return null;
        }
        int second = -1;
        try {
            second = time.get("second").getAsInt();
        } catch (NumberFormatException e) {
            return null;
        }
        int nano = -1;
        try {
            nano = time.get("nano").getAsInt();
        } catch (NumberFormatException e) {
            return null;
        }
        JsonObject zone = jsonObject.get("zone").getAsJsonObject();
        ZoneId zoneId = null;
        try {
            zoneId = ZoneId.of(zone.get("id").getAsString());
        } catch (ZoneRulesException e) {
            zoneId = null;
        }

        try {
            return ZonedDateTime.of(year, month, day, hour, minute, second, nano, zoneId);
        } catch (DateTimeException e) {
            return null;
        } catch (NullPointerException e) {
            return null;
        }

    }
}
