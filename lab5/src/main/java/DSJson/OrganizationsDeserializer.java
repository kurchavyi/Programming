package DSJson;

import Organization.Organization;
import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;
import Organization.Address;
import Organization.Coordinates;

/**
 *  class for deserialization from json to TreeMap<Integer, Organization>.
 */
public class OrganizationsDeserializer implements JsonDeserializer<TreeMap<Integer, Organization>> {
    /**
     *  deserialize from json to TreeMap<Integer, Organization>.
     * @return TreeMap <Integer, Organization>.
     */
    @Override
    public TreeMap<Integer, Organization> deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        TreeMap<Integer, Organization> organizations = new TreeMap<>();
        JsonObject jsonObject = json.getAsJsonObject();
        Gson gson = new GsonBuilder().registerTypeAdapter(Organization.class, new OrganizationDeserializer())
                .registerTypeAdapter(Address.class, new AddressDeserializer())
                .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
                .registerTypeAdapter(ZonedDateTime.class, new ZoneDateTimeDeserializer())
                .registerTypeAdapter(TreeMap.class, new OrganizationsDeserializer()).create();
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            Organization organization = gson.fromJson(entry.getValue(), Organization.class);
            try {
                organizations.put(Integer.parseInt(entry.getKey()), organization);
            }
            catch (NumberFormatException nfe)
                {
                    System.out.println("NumberFormatException: " + nfe.getMessage());
                }
        }
        return organizations;
    }
}
