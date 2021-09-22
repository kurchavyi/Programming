package Server;

import data.Organization.Organization;
import data.User;

import java.time.LocalDateTime;
import java.util.*;

/**
 * manages collection in console application.
 */
public class CollectionManager {
    /**
     * TreeMap collection for keeping a collection as java-object.
     */
    private TreeMap<Integer, Organization> organizations = new TreeMap<Integer, Organization>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1-o2;
        }
    });
    private Map<Integer, Organization> synchronizedOrganization = Collections.synchronizedMap(organizations);
    /**
     * date of the last initialization of the collection.
     */
    private LocalDateTime lastInitTime;
    /**
     * date of the last save of the collection.
     */
    private LocalDateTime lastSaveTime;

    private DatabaseCollectionManager databaseCollectionManager;

    public CollectionManager(DatabaseCollectionManager databaseCollectionManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.databaseCollectionManager = databaseCollectionManager;
        loadCollection();
    }

    /**
     * @return The collection itself.
     */
    public Map<Integer, Organization> getCollection() {
        return synchronizedOrganization;
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return synchronizedOrganization.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return synchronizedOrganization.size();
    }

    /**
     * Adds a new organization to collection.
     * @param organization A Organization to add.
     * @param key Organization.
     */
    public void addToCollection(Integer key, Organization organization) {
        if (synchronizedOrganization != null) {
            synchronizedOrganization.put(key, organization);
        } else {
            System.out.println("hear Null, нужно обработать");
        }
    }

    public int getCountElementsThisUser(User user) {
        int count = 0;
        for (Map.Entry<Integer, Organization> entry : synchronizedOrganization.entrySet()) {
            if (entry.getValue().getOwner().equals(user)) {
                count += 1;
            }
        }
        return count;
    }

    /**
     * Removes organization to collection.
     * @param key of Organization A Integer to remove.
     */
    public void removeFromCollection(Integer key) {
        synchronizedOrganization.remove(key);
    }

    public void removeById(Integer id) {
        Integer delete_key = null;
        for (Map.Entry<Integer, Organization> entry : synchronizedOrganization.entrySet()){
            if (entry.getValue().getId().equals(id)) {
                delete_key = entry.getKey();
            }
        }
        synchronizedOrganization.remove(delete_key);
    }
    public boolean isExistOrganizationThisKey(Integer key) {
        return synchronizedOrganization.containsKey(key);
    }
    /**
     * @return organization id from the collection by the given FullName or
     * null if collection is there is no such organization in the collection.
     */
    public Integer getOrganizationByFullName(String fullName){
        for (Map.Entry<Integer, Organization> entry : synchronizedOrganization.entrySet()) {
            if (entry.getValue().getFullName().equals(fullName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Clears the collection.
     */
    public void clearCollection(ArrayList<Integer> idOrganizations) {
        for (Integer id : idOrganizations) {
            removeById(id);
        }
    }

    public int getIdFromKey(Integer key) {
        return synchronizedOrganization.get(key).getId();
    }

    public void loadCollection() {
        synchronizedOrganization = databaseCollectionManager.getCollection();
        lastInitTime = LocalDateTime.now();
        System.out.println("Collection loaded.");
    }

    /**
     * @return organization from the collection whose creationDate field value is the minimum.
     */
    public Organization getOrganizationMinByCreationDate() {
        Organization minOrganization = null;
        for (Organization organization : synchronizedOrganization.values()) {
            if (minOrganization == null) {
                minOrganization = organization;
            }
            else minOrganization = data.Organization.Organization.getMinCreationDate(minOrganization, organization);
        }
        return minOrganization;
    }

    /**
     *
     * @param id Organization
     * @return true if there is an organization with the given key
     */
    public boolean isExistOrganizationThisId(Integer id) {
        if (!synchronizedOrganization.isEmpty()) {
            for (Organization organization : synchronizedOrganization.values()) {
                if (id.equals(organization.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param organization to check
     * @return true if organization is valid
     */
    public boolean checkOrganization(Organization organization) {
        if (organization.getId() == null) {
            return false;
        }
        if (isExistOrganizationThisId(organization.getId())) {
            return false;
        }
        if (organization.getId() <= 0) {
            return false;
        }
        if (organization.getName() == null) {
            return false;
        }
        if (organization.getName().equals("")) {
            return false;
        }
        if (organization.getCoordinates().getX() == null) {
            return false;
        }
        if (organization.getCoordinates().getX() <= -340) {
            return false;
        }
        if (organization.getCoordinates().getY() == null) {
            return false;
        }
        if (organization.getAnnualTurnover() == null) {
            return false;
        }
        if (organization.getAnnualTurnover() <= 0) {
            return false;
        }
        if (organization.getFullName() == null) {
            return false;
        }
        if ((organization.getEmployeesCount() != null) && (organization.getEmployeesCount() <= 0)) {
            return false;
        }
        if (organization.getType() == null) {
            return false;
        }
        if (organization.getCoordinates() != null) {
            if (organization.getCoordinates().getX() == null) {
                return false;
            }
            if ((organization.getCoordinates().getX() != null) && (organization.getCoordinates().getX() <= - 340)){
                return false;
            }
            if (organization.getCoordinates().getY() == null) {
                return false;
            }
        }
        if (organization.getPostalAddress() != null) {
            if (organization.getPostalAddress().getStreet() == null) {
                return false;
            }
            if (organization.getPostalAddress().getStreet().equals("")) {
                return false;
            }
            if (organization.getPostalAddress().getZipCode() == null) {
                return false;
            }
            if (organization.getPostalAddress().getZipCode().length() < 3) {
                return false;
            }
        }
        if (organization.getCreationDate() == null) {
            return false;
        }
        return true;
    }

    /**
     * @return sorted list of organizations.
     */
    public ArrayList<Organization> getArrayListSortOrganization() {
        ArrayList<Organization> array = new ArrayList<Organization>(synchronizedOrganization.values());
        array.sort(Organization.AnnualTurnoverComparator);
        return array;
    }

    /**
     *Method for printing CollectionManager-class object into string representation.
     * @return a string that contains information about the collection.
     */
    @Override
    public String toString() {
        StringBuilder toOut = new StringBuilder();
        if (synchronizedOrganization != null) {
            int thisSize = 0;
            for (Map.Entry<Integer, Organization> entry : synchronizedOrganization.entrySet()) {
                if (thisSize != synchronizedOrganization.size()-1) {
                    toOut.append(entry.getKey()).append(" = ").append(entry.getValue()).append(",\n");
                    thisSize +=1;
                }
                else {
                    toOut.append(entry.getKey()).append(" = ").append(entry.getValue());
                }
            }
        }
        else return "";
        return "CollectionManager {\n" + toOut + "\n}";
    }
}
