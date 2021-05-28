package Organization;

import java.time.ZonedDateTime;
import java.util.Comparator;

/**
 * Class for describing a person - element of collection
 */
public class Organization implements Comparable<Organization>{
    /**
     * field id, The field cannot be null, the field value must be greater than 0
     * The value of this field must be unique, the value of this field must be generated automatically
     */
    private Integer id;
    /**
     * field nextid, to generate a new id
     */
    public static Integer nextid = 1;
    /**
     * field name, field cannot be null, String cannot be empty
     */
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
    private java.time.ZonedDateTime creationDate;

    public Organization(){}
    public Organization(String name, Coordinates coordinates, Float annualTurnover, String fullName,
                        Integer employeesCount, OrganizationType type, Address postalAddress) {
        this.setId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
    }

    public Organization(Integer id, String name, Coordinates coordinates, Float annualTurnover,
                        String fullName, Integer employeesCount, OrganizationType type,
                        Address postalAddress, ZonedDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.postalAddress = postalAddress;
        this.creationDate = creationDate;
    }

    /**
     * @return field id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return field nextid
     */
    public static Integer getNextId() {
        return nextid;
    }

    /**
     * @return field name
     */
    public String getName() {
        return name;
    }

    /**
     * @return field coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return field creation date
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @return field annual turnover
     */
    public Float getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * @return field full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @return field employees count
     */
    public Integer getEmployeesCount() {
        return employeesCount;
    }

    /**
     * @return field organization type
     */
    public OrganizationType getType() {
        return type;
    }

    /**
     * @return field Address
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * add id
     */
    public void setId() {
        this.id = nextid;
        nextid += 1;
    }

    /**
     * increases nextid
     */
    public static void setNextid(Integer nextid) {
        Organization.nextid = nextid;
    }

    /**
     * @param name to which you need to replace
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param coordinates to which you need to replace
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @param creationDate to which you need to replace
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @param annualTurnover to which you need to replace
     */
    public void setAnnualTurnover(Float annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    /**
     * @param fullName to which you need to replace
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @param employeesCount to which you need to replace
     */
    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    /**
     * @param type to which you need to replace
     */
    public void setType(OrganizationType type) {
        this.type = type;
    }

    /**
     * @param postalAddress to which you need to replace
     */
    public void setPostalAddress(Address postalAddress) {
        this.postalAddress = postalAddress;
    }

    /**
     * @param o1 first Organization
     * @param o2 second Organization
     * @return organization with a minimum creation date
     */
    public static Organization getMinCreationDate(Organization o1, Organization o2) {
        ZonedDateTime date1 = o1.getCreationDate();
        ZonedDateTime date2 = o2.getCreationDate();
        if (date1.getYear() != date2.getYear()) {
            if (date1.getYear() < date2.getYear()) {
                return o1;
            } else return o2;
        } else {
            if (date1.getMonth() != date2.getMonth()) {
                if (date1.getMonth().getValue() < date2.getMonth().getValue()) {
                    return o1;
                } else return o2;
            } else {
                if (date1.getDayOfMonth() != date2.getDayOfMonth()) {
                    if (date1.getDayOfMonth() < date2.getDayOfMonth()) {
                        return o1;
                    } else return o2;
                }
                else {
                    if (date1.getHour() != date2.getHour()) {
                        if (date1.getHour() < date2.getHour()) {
                            return o1;
                        } else return o2;
                    }
                    else {
                        if (date1.getMinute() != date2.getMinute()) {
                            if (date1.getMinute() < date2.getMinute()) {
                                return o1;
                            } else return o2;
                        }
                        else {
                            if (date1.getSecond() != date2.getSecond()) {
                                if (date1.getSecond() < date2.getSecond()) {
                                    return o1;
                                } else return o2;
                            }
                            else {
                                if (date1.getNano() != date2.getNano()) {
                                    if (date1.getNano() < date2.getNano()) {
                                        return o1;
                                    } else return o2;
                                }
                                return o1;
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * Method for printing this field into a string representation
     */
    @Override
    public String toString() {
        // делать проверку на поля на null
        String stringEmployeesCount = "null";
        if (getEmployeesCount() != null) {
            stringEmployeesCount = getEmployeesCount().toString();
        }
        String stringPostalAddress = "null";
        if (getPostalAddress() != null) {
            stringPostalAddress = getPostalAddress().toString();
        }
        return "\n{" + " id: "+ id.toString() + "\n" + " name: " + getName() + "\n" + " coordinates: " +
                getCoordinates().toString() + "\n" + " annual turnover: " + getAnnualTurnover().toString() + "\n" +
                " full name: " + getFullName() + "\n" + " employees count: " + stringEmployeesCount +
                "\n" + " type: " + getType().getName() + "\n" + " postal address: " + stringPostalAddress +
                "\n" + " creation date: " + getCreationDate().toString()  + "}";
    }

    /**
     * compares by field annual turnover
     */
    public static Comparator<Organization> AnnualTurnoverComparator = new Comparator<Organization>() {
        @Override
        public int compare(Organization e1, Organization e2) {
            return e1.getAnnualTurnover().compareTo(e2.getAnnualTurnover());
        }
    };

    /**
     * @param o1 organization to be compared
     */
    @Override
    public  int compareTo(Organization o1) {
        return (int) Math.signum(this.annualTurnover - o1.getAnnualTurnover());
    }
}