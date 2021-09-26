package Server;

import Server.Organization.*;
import java.time.LocalDateTime;
import java.util.*;
import static Server.Organization.Organization.getMinCreationDate;

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
    /**
     * FileManager to manage the file.
     */
    private final FileManager fileManager;
    /**
     * date of the last initialization of the collection.
     */
    private LocalDateTime lastInitTime;
    /**
     * date of the last save of the collection.
     */
    private LocalDateTime lastSaveTime;

    public CollectionManager(FileManager fileManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.fileManager = fileManager;
        loadCollection();
    }

    /**
     * @return The collection itself.
     */
    public TreeMap<Integer, Organization> getCollection() {
        return organizations;
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
        return organizations.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return organizations.size();
    }

    /**
     * @return The last element of the collection or null if collection is empty.
     */
    public Organization getLast() {
        return organizations.get(organizations.lastKey());
    }

    /**
     * Adds a new organization to collection.
     * @param organization A Organization to add.
     * @param key Organization.
     */
    public void addToCollection(Integer key, Organization organization) {
        if (organizations != null) {
            organizations.put(key, organization);
        } else {
            System.out.println("hear Null, нужно обработать");
        }
    }

    /**
     * Removes organization to collection.
     * @param key of Organization A Integer to remove.
     */
    public void removeFromCollection(Integer key) {
        organizations.remove(key);
    }

    public boolean isExistOrganizationThisKey(Integer key) {
        return organizations.containsKey(key);
    }
    /**
     * @return organization id from the collection by the given FullName or
     * null if collection is there is no such organization in the collection.
     */
    public Integer getOrganizationByFullName(String fullName){
        for (Map.Entry<Integer, Organization> entry : organizations.entrySet()) {
            if (entry.getValue().getFullName().equals(fullName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Clears the collection.
     */
    public void clearCollection() {
        organizations.clear();
    }

    /**
     * Saves the collection to file.
     * @return true, if if it was possible to write data to the file, else false
     */
    public boolean save() {
        lastSaveTime = LocalDateTime.now();
        return fileManager.write(organizations);
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection(){
        TreeMap<Integer, Organization> organizationsInCorrected = fileManager.read();
        int nextId = 1;
        if (organizationsInCorrected != null) {
            int countInCorrected = organizationsInCorrected.size();
            for (Map.Entry<Integer, Organization> entry : organizationsInCorrected.entrySet()) {
                if (checkOrganization(entry.getValue())) {
                    organizations.put(entry.getKey(), entry.getValue());
                    if (entry.getValue().getId() > nextId) {
                        nextId = entry.getValue().getId();
                    }
                }
            }
            lastInitTime = LocalDateTime.now();
            System.out.println("there were " + countInCorrected + " organizations in the file");
            System.out.println(organizations.size() + " organizations have been validated");
            if (nextId > Organization.getNextId()) {
                Organization.setNextid(nextId + 1);
            }
        }
    }

    /**
     * @return organization from the collection whose creationDate field value is the minimum.
     */
    public Organization getOrganizationMinByCreationDate() {
        Organization minOrganization = null;
        for (Organization organization : organizations.values()) {
            if (minOrganization == null) {
                minOrganization = organization;
            }
            else minOrganization = getMinCreationDate(minOrganization, organization);
        }
        return minOrganization;
    }

    /**
     *
     * @param id Organization
     * @return true if there is an organization with the given key
     */
    public boolean isExistOrganizationThisId(Integer id) {
        if (!organizations.isEmpty()) {
            for (Organization organization : organizations.values()) {
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
        ArrayList<Organization> array = new ArrayList<Organization>(organizations.values());
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
        if (organizations != null) {
            int thisSize = 0;
            for (Map.Entry<Integer, Organization> entry : organizations.entrySet()) {
                if (thisSize != organizations.size()-1) {
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

    public FileManager getFileManager() {
        return fileManager;
    }
}
