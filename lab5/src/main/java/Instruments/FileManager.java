package Instruments;

import DSJson.*;
import Organization.Organization;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.TreeMap;
import Organization.*;

/**
 * class for file management.
 */
public class FileManager {
    /**
     * path to the file where the collection is stored
     */
    private Path path;
    /**
     * marshal Organization in json
     */
    private MarshalJson marshalJson;
    /**
     * marshal json in Organization
     */
    private MarshalOrganization marshalOrganization;

    public FileManager(Path path) {
        this.path = path;
        this.marshalJson = new MarshalJson(new GsonBuilder().setPrettyPrinting().create());
        this.marshalOrganization = new MarshalOrganization(new GsonBuilder().
                registerTypeAdapter(Organization.class, new OrganizationDeserializer())
                .registerTypeAdapter(Address.class, new AddressDeserializer())
                .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
                .registerTypeAdapter(ZonedDateTime.class, new ZoneDateTimeDeserializer())
                .registerTypeAdapter(TreeMap.class, new OrganizationsDeserializer()).create());
    }

    /**
     * writes data to a file
     * @param map collection
     */
    public void write(TreeMap<Integer, Organization> map) {
        File file = path.toFile();
        if (isCanWrite(file)) {
            String outputFileName = path.toString();
            try (BufferedWriter writter = new BufferedWriter(new FileWriter(outputFileName))) {
                writter.write(marshalJson.Organizations(map));
            } catch (IOException e) {
                System.out.println("there are some problems with the file");
            }
        }
    }

    /**
     * read data from a file
     */
    public TreeMap<Integer, Organization> read() {
        File file = path.toFile();
        if (isCanRead(file)) {
            String inputFileName = path.toString();
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
                String line;
                String data = "";
                String prevData = "";
                while ((line = reader.readLine()) != null) {
                    if (data.length() != 0) {
                        prevData = data + "\n";
                    }
                    data = prevData + line;
                }
                if (data.equals("")) {
                    return new TreeMap<Integer, Organization>();
                }
                return (TreeMap<Integer, Organization>) marshalOrganization.Json(data);
            } catch (IOException e) {
                System.out.println("There are some problems with the file.");
                return null;
            }
        }
        System.out.println("The application is not running. Try again.");
        System.exit(1);
        return null;
    }

    /**
     * checks the file for readability
     * @return true, if the file can be read
     */
    public static boolean isCanRead(File file) {
        if (!file.exists()) {
            System.out.println("The file at the specified path does not exist.");
            return false;
        }
        if (!file.canRead()) {
            System.out.println("The file at the specified path could not be read.");
            return false;
        }
        return true;
    }

    /**
     * checks the file for writeability
     * @return true, if the file can be write
     */
    public static boolean isCanWrite(File file) {
        if (!file.exists()) {
            System.out.println("the file at the specified path does not exist");
            return false;
        }
        if (!file.canWrite()) {
            System.out.println("data cannot be written to this file");
            return false;
        }
        return true;
    }
}