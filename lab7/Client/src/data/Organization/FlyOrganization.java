package data.Organization;

import java.io.Serializable;
import java.util.Objects;

public class FlyOrganization implements Serializable {

    private String name;
    /**
     * field coordinates, The field cannot be null
     */
    private Coordinates coordinates;
    /**
     *  field annual turnover, Field can be null, Field value must be greater than 0
     */
    private Float annualTurnover;
    /**
     * field full name, The field cannot be null
     */
    private String fullName;
    /**
     * field employees count, Field can be null, Field value must be greater than 0
     */
    private Integer employeesCount;
    /**
     * field organization type, The field cannot be null
     */
    private OrganizationType type;
    /**
     * field postal address, The field can be null
     */
    private Address postalAddress;
    /**
     * field creation date, The field cannot be null, the value of this field must be generated automatically
     */

    public FlyOrganization(String name, Coordinates coordinates, Float annualTurnover, String fullName, Integer employeesCount, OrganizationType type, Address postalAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    public FlyOrganization(){}

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public OrganizationType getType() {
        return type;
    }

    public Address getPostalAddress() {
        return postalAddress;
    }

    public  int compareTo(Organization o1) {
        return (int) Math.signum(this.annualTurnover - o1.getAnnualTurnover());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlyOrganization organization = (FlyOrganization) o;
        return Objects.equals(annualTurnover, organization.annualTurnover) &&
                Objects.equals(fullName, organization.fullName) &&
                employeesCount == organization.employeesCount &&
                Objects.equals(name, organization.name) &&
                Objects.equals(coordinates, organization.coordinates) &&
                type == organization.type &&
                Objects.equals(postalAddress, organization.postalAddress);
    }
}
