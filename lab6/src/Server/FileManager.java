package Server;

import Server.DSJson.*;
import Server.Organization.Organization;
import Server.Instruments.Marshal;
import Server.Instruments.MarshalJson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.TreeMap;
import Server.Organization.*;

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
    private final MarshalJson marshalJson;
    /**
     * marshal json in Organization
     */
    private final Marshal marshalOrganization;

    public FileManager(Path path) {
        this.path = path;
        this.marshalJson = new MarshalJson();
        this.marshalOrganization = new Marshal();
    }

    /**
     * writes data to a file
     * @param map collection
     * @return true, if if it was possible to write data to the file, else false
     */
    public boolean write(TreeMap<Integer, Organization> map) {
        File file = path.toFile();
        if (isCanWrite(file)) {
            String outputFileName = path.toString();
            try (BufferedWriter writter = new BufferedWriter(new FileWriter(outputFileName))) {
                writter.write(marshalJson.Organizations(map));
                return true;
            } catch (IOException e) {
                System.out.println("there are some problems with the file");
                return false;
            }
        } return false;
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
                return (TreeMap<Integer, Organization>) marshalOrganization.JsonTreeMap(data);
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

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}