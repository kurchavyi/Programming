package Client.Organization;

/**
 * Class for describing field Coordinates of element
 */
public class Address {
    /**
     * field street
     */
    private String street;
    /**
     * field zipCode
     */
    private String zipCode;

    public Address(){}
    public Address(String street, String zipCode){
        this.street = street;
        this.zipCode = zipCode;
    }

    /**
     * @return field street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @return field zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param street to which you need to replace
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @param zipCode to which you need to replace
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Method for printing this field into a string representation
     */
    @Override
    public String toString() {
        return "{\n  street: " + this.getStreet()+ "\n" + "  zip code: " + this.getZipCode() + " }";
    }
}