package Client.Instruments;

import Client.Organization.Address;
import Client.Organization.Coordinates;
import Client.Organization.Organization;
import Client.Organization.OrganizationType;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * class which reads data from the command line
 */
public class UserScanner {
    /**
     * user scanner which reads data from the command line
     */
    private final Scanner userScanner;

    public UserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    /**
     * @return command and argument, which the user entered
     */
    public String[] enterCommand() {
        String[] userCommand = {"", ""};
        try {
            System.out.println("enter the required command");
            userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
            userCommand[1] = userCommand[1].trim();
        } catch (NoSuchElementException exception) {
            System.out.println("The input stream is over. The program is stopped");
            System.exit(1);
            return userCommand;
        } catch (IllegalStateException exception) {
            System.out.println("unexpected error");
        }
        return userCommand;
    }

    /**
     * @return name, which the user entered
     */
    public String enterName() {
        String name = null;
        while (true) {
            try {
                System.out.println("enter the name of the organization. Name cannot be empty string");
                name = userScanner.nextLine();
                if (!name.equals("")) {
                    return name;
                }
            } catch (NoSuchElementException exception) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
            System.out.println("input is incorrect");
        }
    }

    /**
     * @return Coordinates, which the user entered
     */
    public Coordinates enterCoordinates() {
        System.out.println("enter coordinates");
        int x= 0;
        double y = 0.0;
        while (true) {
            System.out.println("enter the x coordinate, x is a integer, x > -340");
            try {
                x = Integer.parseInt(userScanner.nextLine());
                if (x > -340) {
                    break;
                }
                else {
                    System.out.println("input is incorrect");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("input is incorrect");
            } catch (NoSuchElementException exception) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
        }
        while (true) {
                System.out.println("enter the y coordinate, y is a double");
            try {
                y = Double.parseDouble(userScanner.nextLine());
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("input is incorrect");
            }  catch (NoSuchElementException exception) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
        }
        return new Coordinates(x, y);
    }

    /**
     * @return annual turnover, which the user entered
     */
    public Float enterAnnualTurnover() {
        Float annualTurnover = null;
        while (true) {
            try {
                System.out.println("enter the annual turnover, annual turnover is a Float, annual turnover > 0");
                annualTurnover = Float.parseFloat(userScanner.nextLine());
                if (annualTurnover > 0) {
                    break;
                }
            } catch (NumberFormatException cfe) {
                System.out.println("input is incorrect");
            } catch (NoSuchElementException exception) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
        }
        return annualTurnover;
    }

    /**
     * @return full name, which the user entered
     */
    public String enterFullName() {
        String fullName = null;
        System.out.println("enter the full name of the organization");
        try {
            fullName = userScanner.nextLine();
        } catch (NoSuchElementException exception) {
            System.out.println("The input stream is over. The program is stopped");
            System.exit(1);
        }
        return fullName;
    }

    /**
     * @return employees count , which the user entered
     */
    public Integer enterEmployeesCount() {
        Integer employeesCount = null;
        String stringEmployeesCount;
        while (true) {
            try {
                System.out.println("enter the employees count, employees count is a integer, employees count > 0");
                stringEmployeesCount = userScanner.nextLine();
                if (stringEmployeesCount.equals("")) {
                    return null;
                }
                employeesCount = Integer.parseInt(stringEmployeesCount);
                if (employeesCount > 0) {
                    break;
                }
            } catch (NumberFormatException cfe) {
                System.out.println("input is incorrect");
            } catch (NoSuchElementException exception) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
        }
        return employeesCount;
    }

    /**
     * @return organization type, which the user entered
     */
    public OrganizationType enterOrganizationType() {
        System.out.println("enter organization type. Here is a list of possible types: 'commercial', 'trust', " +
                "'open joint stock company'. Warning: enter exactly as in the list");
        String type = null;
        while (true) {
            try {
                type = userScanner.nextLine();
            } catch (NoSuchElementException exception) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
            type = type.toLowerCase();
            if (type.equals("commercial")) {
                return OrganizationType.COMMERCIAL;
            }
            if (type.equals("trust")) {
                return OrganizationType.TRUST;
            }
            if (type.equals("open joint stock company")) {
                return OrganizationType.OPEN_JOINT_STOCK_COMPANY;
            }
            System.out.println("input is incorrect");
        }
    }

    /**
     * @return postal address, which the user entered
     */
    public Address enterPostalAddress() {
        int check = -1;
        while (true) {
            System.out.println("if the address is unknown then enter 0, otherwise 1");
            try {
                String stringCheck = userScanner.nextLine();
                check = Integer.parseInt(stringCheck);
                if ((check == 1) || (check == 0)) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("input is incorrect, enter 0 or 1");
            } catch (NoSuchElementException e) {
                System.out.println("The input stream is over. The program is stopped");
            }
        }
        if (check == 0) {
            return null;
        }
        System.out.println("enter postal address");
        String street = null;
        String zipcode = null;
        while (true) {
            try {
                System.out.println("enter street, street cannot be empty line");
                street = userScanner.nextLine();
                if (!street.equals("")) {
                    break;
                }
            } catch (NoSuchElementException exception) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
            System.out.println("input is incorrect");
        }
        while (true) {
            System.out.println("enter the zipcode, length string >= 3");
            try {
                zipcode = userScanner.nextLine();
                if (zipcode.length() >= 3) {
                    break;
                } else {
                    System.out.println("input is incorrect");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("input is incorrect");
            } catch (NoSuchElementException exception) {
                System.out.println("The input stream is over. The program is stopped");
                System.exit(1);
            }
        }
        return new Address(street, zipcode);
    }
    /**
     * @return organization, which the user entered
     */
    public Organization enterOrganization() {
        String name = enterName();
        Coordinates coordinates = enterCoordinates();
        Float annualTurnover = enterAnnualTurnover();
        String fullName = enterFullName();
        Integer employeesCount = enterEmployeesCount();
        OrganizationType type = enterOrganizationType();
        Address postalAddress = enterPostalAddress();
        return new Organization(name, coordinates, annualTurnover, fullName, employeesCount, type, postalAddress);
    }
}
