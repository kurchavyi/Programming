package Server.Instruments;

import Server.Organization.Organization;
import Server.DSJson.*;
import Server.Organization.Address;
import Server.Organization.Coordinates;
import Server.TransferWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.ZonedDateTime;
import java.util.TreeMap;

public class Marshal {

    /**
     * Object gson for marshal.
     */
    private Gson gson;

    /**
     * marshal from json to organization.
     */
    public Marshal() {
        this.gson = new GsonBuilder().
                registerTypeAdapter(Organization.class, new OrganizationDeserializer())
                .registerTypeAdapter(Address.class, new AddressDeserializer())
                .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
                .registerTypeAdapter(ZonedDateTime.class, new ZoneDateTimeDeserializer())
                .registerTypeAdapter(TreeMap.class, new OrganizationsDeserializer())
                .registerTypeAdapter(TreeMap.class, new OrganizationsDeserializer())
                .registerTypeAdapter(Server.TransferWrapper.class, new TransferWrapperDeserializer()).create();
    }

    /**
     * @return field gson.
     */
    public Gson getGson() {
        return gson;
    }
    /**
     * Method for passing a value gson field.
     * @param gson, new value.
     */
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    /**
     * @param json String  which needs to be translated into Organization.
     * @return Organizations TreeMap<Integer, Organization>.
     */
    public TransferWrapper JsonTransferWrapper(String json){
        return gson.fromJson(json, Server.TransferWrapper.class);
    }
    public TreeMap<Integer, Organization> JsonTreeMap(String json) {
        return gson.fromJson(json, TreeMap.class);
    }

    public Organization JsonOrganization(String json) {
        return gson.fromJson(json, Organization.class);
    }
}
