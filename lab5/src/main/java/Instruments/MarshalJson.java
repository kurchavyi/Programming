package Instruments;

import Organization.Organization;
import com.google.gson.Gson;
import java.util.TreeMap;

/**
 * class to marshal from organization to json.
 */
public class MarshalJson {

    /**
     * Object gson for marshal.
     */
    private Gson gson;

    /**
     * marshal from organization to json.
     */
    public MarshalJson(Gson gson) {
        this.gson = gson;
    }

    /**
     * Method for passing a value gson field.
     * @param gson, new value.
     */
    public void setGson(Gson gson) {
        this.gson = gson;
    }

    /**
     * @return field gson.
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * @param organization which needs to be translated into json.
     * @return json organization.
     */
    public String Organization(Organization organization) {
        return gson.toJson((organization));
    }

    /**
     * @param organizations which needs to be translated into json.
     * @return json organizations.
     */
    public String Organizations(TreeMap<Integer, Organization> organizations) {
        return gson.toJson(organizations);
    }
}
