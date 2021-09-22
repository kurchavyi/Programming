package data.Organization;

import java.io.Serializable;

/**
 * Enum class for describing type of organization
 */
public enum OrganizationType implements Serializable {
    COMMERCIAL("commercial"),
    TRUST("trust"),
    OPEN_JOINT_STOCK_COMPANY("open joint stock company");

    private String name;

    OrganizationType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}