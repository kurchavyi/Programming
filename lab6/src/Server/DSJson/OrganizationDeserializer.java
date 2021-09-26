package Server.DSJson;

import com.google.gson.*;
import Server.Organization.*;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;

/**
 *  class for deserialization from json to Client.Organization.
 */
public class OrganizationDeserializer implements JsonDeserializer<Organization> {
    /**
     *  deserialize from json to Client.Organization.
     * @return Client.Organization.
     */
    @Override
    public Organization deserialize(JsonElement jsonElement, Type type,
                                    JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        int id = 0;
        try {
            id = jsonObject.get("id").getAsInt();
        } catch (NumberFormatException e) {
            id = 0;
        }
        String name = jsonObject.get("name").getAsString();
        Coordinates coordinates = (Coordinates) jsonDeserializationContext.deserialize(jsonObject.get("coordinates"), Coordinates.class);
        Float annualTurnover = null;
        try {
            annualTurnover = jsonObject.get("annualTurnover").getAsFloat();
        } catch (NumberFormatException e) {
            annualTurnover = null;
        }
        String fullName = jsonObject.get("fullName").getAsString();
        Integer employeesCount = null;
        if (jsonObject.get("employeesCount") != null) {
            try {
                employeesCount = jsonObject.get("employeesCount").getAsInt();
            } catch (NumberFormatException e) {
                employeesCount = -1;
            }

        }
        OrganizationType typeOrganization = null;
        try {
            typeOrganization = OrganizationType.valueOf(jsonObject.get("type").getAsString());
        } catch (IllegalArgumentException e) {
            typeOrganization = null;
        }
        Address postalAddress = (Address) jsonDeserializationContext.deserialize(jsonObject.get("postalAddress"), Address.class);
        ZonedDateTime creationDate = (ZonedDateTime) jsonDeserializationContext.deserialize(jsonObject.get("creationDate"), ZonedDateTime.class);
        return new Organization(id, name, coordinates, annualTurnover, fullName, employeesCount, typeOrganization, postalAddress, creationDate);
    }
}
