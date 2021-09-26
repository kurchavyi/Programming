package Server.DSJson;

import Server.TransferWrapper;
import Server.Organization.Address;
import Server.Organization.Coordinates;
import Server.Organization.Organization;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.TreeMap;

public class TransferWrapperDeserializer implements JsonDeserializer<TransferWrapper> {
    @Override
    public TransferWrapper deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TransferWrapper transferWrapper = new TransferWrapper();
        Gson gson = new GsonBuilder().registerTypeAdapter(Organization.class, new OrganizationDeserializer())
                .registerTypeAdapter(Address.class, new AddressDeserializer())
                .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
                .registerTypeAdapter(ZonedDateTime.class, new ZoneDateTimeDeserializer()).create();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String command = null;
        try {
            command = jsonObject.get("nameCommand").getAsString();
            transferWrapper.setNameCommand(command);
        } catch (NumberFormatException e) {
            command = null;
        }
        if (jsonObject.get("argument") != null) {
            Object argument = gson.fromJson(jsonObject.get("argument"), Object.class);
            transferWrapper.setArgument(argument);
        }
        return transferWrapper;
    }
}
