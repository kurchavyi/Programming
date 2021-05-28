package Instruments;

import com.google.gson.Gson;
import java.util.TreeMap;

public class MarshalOrganization {

    /**
     * Object gson for marshal.
     */
    private Gson gson;

    /**
     * marshal from json to organization.
     */
    public MarshalOrganization(Gson gson) {
        this.gson = gson;
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

//    public Organization JsonToOrganization(String json){
//        return gson.fromJson(json, Organization.class);
//    }
    /**
     * @param json String  which needs to be translated into Organization.
     * @return Organizations TreeMap<Integer, Organization>.
     */
    public TreeMap Json(String json){
        return gson.fromJson(json, TreeMap.class);
    }
}
