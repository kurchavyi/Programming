package Server.DSJson;

import com.google.gson.*;
import Server.Organization.Address;
import java.lang.reflect.Type;

/**
 *  class for deserialization from json to Address.
 */
public class AddressDeserializer implements JsonDeserializer<Address> {
    /**
     *  deserialize from json to Address.
     * @return Address.
     */
    @Override
    public Address deserialize(JsonElement jsonElement, Type type,
                               JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String street = jsonObject.get("street").getAsString();
        String zipcode = jsonObject.get("zipCode").getAsString();
        return new Address(street, zipcode);
    }
}
