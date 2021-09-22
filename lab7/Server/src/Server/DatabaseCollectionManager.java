package Server;

import Exceptions.DatabaseException;
import data.Organization.*;
import data.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class DatabaseCollectionManager {
    private final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM " + DatabaseManager.ORGANIZATIONS_TABLE;
    private final String SELECT_ORGANIZATION_BY_ID = SELECT_ALL_ORGANIZATIONS + " WHERE " +
            DatabaseManager.ORGANIZATIONS_TABLE_ID_COLUMN + " = ?";
    private final String SELECT_ORGANIZATION_ID_AND_USER_ID = SELECT_ORGANIZATION_BY_ID + " AND " +
            DatabaseManager.ORGANIZATIONS_TABLE_USER_ID_COLUMN + " = ?";
    private final String INSERT_ORGANIZATION = "INSERT INTO " +
            DatabaseManager.ORGANIZATIONS_TABLE + " (" +
            DatabaseManager.ORGANIZATIONS_TABLE_NAME_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_ANNUAL_TURNOVER_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_FULL_NAME_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_EMPLOYEES_COUNT_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_COORDINATES_ID_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_ADDRESS_ID_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_ORGANIZATION_TYPE_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_USER_ID_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_CREATION_DATE_COLUMN + ", " +
            DatabaseManager.ORGANIZATIONS_TABLE_KEY_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String DELETE_ORGANIZATION_BY_ID = "DELETE FROM " + DatabaseManager.ORGANIZATIONS_TABLE +
            " WHERE " + DatabaseManager.ORGANIZATIONS_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_ORGANIZATION_NAME_BY_ID = "UPDATE " + DatabaseManager.ORGANIZATIONS_TABLE + " SET " +
            DatabaseManager.ORGANIZATIONS_TABLE_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.ORGANIZATIONS_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_ORGANIZATIONS_ANNUAL_TURNOVER_BY_ID = "UPDATE " + DatabaseManager.ORGANIZATIONS_TABLE + " SET " +
            DatabaseManager.ORGANIZATIONS_TABLE_ANNUAL_TURNOVER_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.ORGANIZATIONS_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_ORGANIZATIONS_FULL_NAME_BY_ID = "UPDATE " + DatabaseManager.ORGANIZATIONS_TABLE + " SET " +
            DatabaseManager.ORGANIZATIONS_TABLE_FULL_NAME_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.ORGANIZATIONS_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_ORGANIZATIONS_EMPLOYEES_COUNT = "UPDATE " + DatabaseManager.ORGANIZATIONS_TABLE + " SET " +
            DatabaseManager.ORGANIZATIONS_TABLE_EMPLOYEES_COUNT_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.ORGANIZATIONS_TABLE_ID_COLUMN + " = ?";
    private final String UPDATE_ORGANIZATIONS_ORGANIZATION_TYPE_BY_ID = "UPDATE " + DatabaseManager.ORGANIZATIONS_TABLE + " SET " +
            DatabaseManager.ORGANIZATIONS_TABLE_ORGANIZATION_TYPE_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.ORGANIZATIONS_TABLE_ID_COLUMN + " = ?";

    private final String SELECT_ALL_COORDINATES = "SELECT * FROM " + DatabaseManager.COORDINATES_TABLE;
    private final String SELECT_COORDINATES_BY_ID = SELECT_ALL_COORDINATES + " WHERE " + DatabaseManager.COORDINATES_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_COORDINATES_BY_ID = "DELETE FROM " + DatabaseManager.COORDINATES_TABLE +
            " WHERE " + DatabaseManager.COORDINATES_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_COORDINATES = "INSERT INTO " +
            DatabaseManager.COORDINATES_TABLE + " (" +
            DatabaseManager.COORDINATES_TABLE_X_COLUMN + ", " +
            DatabaseManager.COORDINATES_TABLE_Y_COLUMN + ") VALUES (?, ?)";
    private final String UPDATE_COORDINATES_BY_ID = "UPDATE " + DatabaseManager.COORDINATES_TABLE + " SET " +
            DatabaseManager.COORDINATES_TABLE_X_COLUMN + " = ?, " +
            DatabaseManager.COORDINATES_TABLE_Y_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.COORDINATES_TABLE_ID_COLUMN + " = ?";

    private final String SELECT_ALL_ADDRESS = "SELECT * FROM " + DatabaseManager.ADDRESS_TABLE;
    private final String SELECT_ADDRESS_BY_ID = SELECT_ALL_ADDRESS + " WHERE " + DatabaseManager.ADDRESS_TABLE_ID_COLUMN + " = ?";
    private final String DELETE_ADDRESS_BY_ID = "DELETE FROM " + DatabaseManager.ADDRESS_TABLE +
            " WHERE " + DatabaseManager.ADDRESS_TABLE_ID_COLUMN + " = ?";
    private final String INSERT_ADDRESS = "INSERT INTO " +
            DatabaseManager.ADDRESS_TABLE + " (" +
            DatabaseManager.ADDRESS_TABLE_STREET_COLUMN + ", " +
            DatabaseManager.ADDRESS_TABLE_ZIP_CODE_COLUMN + ") VALUES (?, ?)";
    private final String UPDATE_ADDRESS_BY_ID = "UPDATE " + DatabaseManager.ADDRESS_TABLE + " SET " +
            DatabaseManager.ADDRESS_TABLE_STREET_COLUMN + " = ?, " +
            DatabaseManager.ADDRESS_TABLE_ZIP_CODE_COLUMN + " = ?" + " WHERE " +
            DatabaseManager.ADDRESS_TABLE_ID_COLUMN + " = ?";

    private DatabaseManager databaseManager;
    private UserManager databaseUserManager;

    public DatabaseCollectionManager(DatabaseManager databaseManager, UserManager databaseUserManager) {
        this.databaseManager = databaseManager;
        this.databaseUserManager = databaseUserManager;
    }

    public Map<Integer, Organization> getCollection() {
        TreeMap<Integer, Organization> organizations = new TreeMap<Integer, Organization>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        Map<Integer, Organization> synchronizedOrganization = Collections.synchronizedMap(organizations);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_ALL_ORGANIZATIONS, false);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(DatabaseManager.ORGANIZATIONS_TABLE_ID_COLUMN);
                int key = resultSet.getInt(DatabaseManager.ORGANIZATIONS_TABLE_KEY_COLUMN);
                synchronizedOrganization.put(key, returnOrganization(resultSet, id));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return synchronizedOrganization;
    }

    private Organization returnOrganization(ResultSet resultSet, int id) throws SQLException {
        String name = resultSet.getString(DatabaseManager.ORGANIZATIONS_TABLE_NAME_COLUMN);
        float annual_turnover = resultSet.getFloat(DatabaseManager.ORGANIZATIONS_TABLE_ANNUAL_TURNOVER_COLUMN);
        String full_name = resultSet.getString(DatabaseManager.ORGANIZATIONS_TABLE_FULL_NAME_COLUMN);
        Integer employees_count = resultSet.getInt(DatabaseManager.ORGANIZATIONS_TABLE_EMPLOYEES_COUNT_COLUMN);
        Coordinates coordinates = getCoordinatesByID(resultSet.getInt(DatabaseManager.ORGANIZATIONS_TABLE_COORDINATES_ID_COLUMN));
        Address address = getAddressById(resultSet.getInt(DatabaseManager.ORGANIZATIONS_TABLE_ADDRESS_ID_COLUMN));
        OrganizationType organizationType = OrganizationType.valueOf(resultSet.getString(DatabaseManager.ORGANIZATIONS_TABLE_ORGANIZATION_TYPE_COLUMN));
        java.sql.Timestamp time = resultSet.getTimestamp(DatabaseManager.ORGANIZATIONS_TABLE_CREATION_DATE_COLUMN);
        LocalDateTime creationDate = (resultSet.getTimestamp(DatabaseManager.ORGANIZATIONS_TABLE_CREATION_DATE_COLUMN)).toLocalDateTime();
        User owner = databaseUserManager.getUserById(resultSet.getInt(DatabaseManager.ORGANIZATIONS_TABLE_USER_ID_COLUMN));
        return new Organization(id, name, coordinates, annual_turnover, full_name, employees_count,
                organizationType, address, creationDate, owner);
    }

    private Coordinates getCoordinatesByID(int id) throws SQLException {
        Coordinates coordinates;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_COORDINATES_BY_ID, false);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                coordinates = new Coordinates(
                        resultSet.getInt(DatabaseManager.COORDINATES_TABLE_X_COLUMN),
                        resultSet.getDouble(DatabaseManager.COORDINATES_TABLE_Y_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("An error occurred while executing the SELECT_COORDINATES_BY_ID query!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return coordinates;
    }

    private Address getAddressById(int id) throws SQLException {
        Address address;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_ADDRESS_BY_ID, false);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                address = new Address(
                        resultSet.getString(DatabaseManager.ADDRESS_TABLE_STREET_COLUMN),
                        resultSet.getString(DatabaseManager.ADDRESS_TABLE_ZIP_CODE_COLUMN)
                );
            } else throw new SQLException();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while executing the SELECT_COORDINATES_BY_ID query!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return address;
    }

    private int getCoordinatesIdByOrganizationID(int organizationID) throws SQLException {
        int coordinatesID;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_ORGANIZATION_BY_ID, false);
            preparedStatement.setInt(1, organizationID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                coordinatesID = resultSet.getInt(DatabaseManager.ORGANIZATIONS_TABLE_COORDINATES_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("An error occurred while executing the SELECT_COORDINATES_BY_ID query!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return coordinatesID;
    }

    private int getAddressIdByOrganizationID(int organizationID) throws SQLException {
        int addressID;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = databaseManager.doPreparedStatement(SELECT_ORGANIZATION_BY_ID, false);
            preparedStatement.setInt(1, organizationID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                addressID = resultSet.getInt(DatabaseManager.ORGANIZATIONS_TABLE_ADDRESS_ID_COLUMN);
            } else throw new SQLException();
        } catch (SQLException e) {
            System.out.println("An error occurred while executing the SELECT_COORDINATES_BY_ID query!");
            throw new SQLException(e);
        } finally {
            databaseManager.closePreparedStatement(preparedStatement);
        }
        return addressID;
    }

    public Organization insertOrganization(FlyOrganization flyOrganization, User user, Integer key) throws DatabaseException {
        Organization organizationToInsert;
        PreparedStatement insertOrganization = null;
        PreparedStatement insertCoordinates = null;
        PreparedStatement insertAddress = null;
        try {
            databaseManager.setCommit();
            databaseManager.setSavepoint();

            LocalDateTime localDateTime = LocalDateTime.now();

            insertCoordinates = databaseManager.doPreparedStatement(INSERT_COORDINATES, true);
            insertCoordinates.setDouble(1, flyOrganization.getCoordinates().getX());
            insertCoordinates.setDouble(2, flyOrganization.getCoordinates().getY());
            if (insertCoordinates.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSetCoordinates = insertCoordinates.getGeneratedKeys();
            int coordinatesID;
            if (resultSetCoordinates.next()) coordinatesID = resultSetCoordinates.getInt(1);
            else throw new SQLException();

            insertAddress = databaseManager.doPreparedStatement(INSERT_ADDRESS, true);
            if (flyOrganization.getPostalAddress() == null) {
                insertAddress.setString(1, null);
                insertAddress.setString(2, null);
            } else {
                insertAddress.setString(1, flyOrganization.getPostalAddress().getStreet());
                insertAddress.setString(2, flyOrganization.getPostalAddress().getZipCode());
            }
            if (insertAddress.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSetAddress = insertAddress.getGeneratedKeys();
            int addressID;
            if (resultSetAddress.next()) addressID = resultSetAddress.getInt(1);
            else throw new SQLException();

            insertOrganization = databaseManager.doPreparedStatement(INSERT_ORGANIZATION, true);
            insertOrganization.setString(1, flyOrganization.getName());
            insertOrganization.setFloat(2, flyOrganization.getAnnualTurnover());
            insertOrganization.setString(3, flyOrganization.getFullName());
            if (flyOrganization.getEmployeesCount() == null) {
                insertOrganization.setNull(4, Types.INTEGER);
            } else {
                insertOrganization.setInt(4, flyOrganization.getEmployeesCount());
            }
            insertOrganization.setInt(5, coordinatesID);
            insertOrganization.setInt(6, addressID);
            insertOrganization.setString(7, flyOrganization.getType().toString());
            insertOrganization.setInt(8, databaseUserManager.getUserIdByUsername(user));
            insertOrganization.setTimestamp(9, Timestamp.valueOf(localDateTime));
            insertOrganization.setInt(10, key);

            if (insertOrganization.executeUpdate() == 0) throw new SQLException();
            ResultSet resultSetGroup = insertOrganization.getGeneratedKeys();
            int organizationId;
            if (resultSetGroup.next()) organizationId = resultSetGroup.getInt(1);
            else throw new SQLException();
            organizationToInsert = new Organization(
                    organizationId,
                    flyOrganization.getName(),
                    flyOrganization.getCoordinates(),
                    flyOrganization.getAnnualTurnover(),
                    flyOrganization.getFullName(),
                    flyOrganization.getEmployeesCount(),
                    flyOrganization.getType(),
                    flyOrganization.getPostalAddress(),
                    localDateTime,
                    user
            );
            databaseManager.commit();
            return organizationToInsert;
        } catch (SQLException exception) {
            System.out.println("An error occurred while adding a new object to the database!");
            exception.printStackTrace();
            databaseManager.rollback();
            throw new DatabaseException();
        } finally {
            databaseManager.closePreparedStatement(insertAddress);
            databaseManager.closePreparedStatement(insertCoordinates);
            databaseManager.closePreparedStatement(insertOrganization);
            databaseManager.setAutoCommit();
        }
    }

    public void updateOrganizationByID(int organizationId, FlyOrganization flyOrganization) throws DatabaseException {
        PreparedStatement updateOrganizationName = null;
        PreparedStatement updateOrganizationAnnualTurnover = null;
        PreparedStatement updateOrganizationFullName = null;
        PreparedStatement updateOrganizationEmployeesCount = null;
        PreparedStatement updateOrganizationCoordinates = null;
        PreparedStatement updateOrganizationAddress = null;
        PreparedStatement updateOrganizationOrganizationType = null;
        try {
            databaseManager.setCommit();
            databaseManager.setSavepoint();

            updateOrganizationName = databaseManager.doPreparedStatement(UPDATE_ORGANIZATION_NAME_BY_ID, false);
            updateOrganizationAnnualTurnover = databaseManager.doPreparedStatement(UPDATE_ORGANIZATIONS_ANNUAL_TURNOVER_BY_ID, false);
            updateOrganizationFullName = databaseManager.doPreparedStatement(UPDATE_ORGANIZATIONS_FULL_NAME_BY_ID, false);
            updateOrganizationEmployeesCount = databaseManager.doPreparedStatement(UPDATE_ORGANIZATIONS_EMPLOYEES_COUNT, false);
            updateOrganizationCoordinates = databaseManager.doPreparedStatement(UPDATE_COORDINATES_BY_ID, false);
            updateOrganizationAddress = databaseManager.doPreparedStatement(UPDATE_ADDRESS_BY_ID, false);
            updateOrganizationOrganizationType = databaseManager.doPreparedStatement(UPDATE_ORGANIZATIONS_ORGANIZATION_TYPE_BY_ID, false);

            updateOrganizationName.setString(1, flyOrganization.getName());
            updateOrganizationName.setInt(2, organizationId);
            if (updateOrganizationName.executeUpdate() == 0) throw new SQLException();

            updateOrganizationAnnualTurnover.setDouble(1, flyOrganization.getAnnualTurnover());
            updateOrganizationAnnualTurnover.setInt(2, organizationId);
            if (updateOrganizationAnnualTurnover.executeUpdate() == 0) throw new SQLException();

            updateOrganizationFullName.setString(1, flyOrganization.getFullName());
            updateOrganizationFullName.setInt(2, organizationId);
            if (updateOrganizationFullName.executeUpdate() == 0) throw new SQLException();

            if (flyOrganization.getEmployeesCount() == null) {
                updateOrganizationEmployeesCount.setNull(1, Types.INTEGER);
                updateOrganizationEmployeesCount.setInt(2, organizationId);
            }
            else {
                updateOrganizationEmployeesCount.setInt(1, flyOrganization.getEmployeesCount());
                updateOrganizationEmployeesCount.setInt(2, organizationId);
            }
            if (updateOrganizationEmployeesCount.executeUpdate() == 0) throw new SQLException();

            updateOrganizationCoordinates.setInt(1, flyOrganization.getCoordinates().getX());
            updateOrganizationCoordinates.setDouble(2, flyOrganization.getCoordinates().getY());
            updateOrganizationCoordinates.setInt(3, getCoordinatesIdByOrganizationID(organizationId));
            if (updateOrganizationCoordinates.executeUpdate() == 0) throw new SQLException();

            if (flyOrganization.getPostalAddress() == null) {
                updateOrganizationAddress.setString(1, null);
                updateOrganizationAddress.setString(2, null);
                updateOrganizationAddress.setInt(3, getAddressIdByOrganizationID(organizationId));
            } else {
                updateOrganizationAddress.setString(1, flyOrganization.getPostalAddress().getStreet());
                updateOrganizationAddress.setString(2, flyOrganization.getPostalAddress().getZipCode());
                updateOrganizationAddress.setInt(3, getAddressIdByOrganizationID(organizationId));
            }
            if (updateOrganizationAddress.executeUpdate() == 0) throw new SQLException();

            updateOrganizationOrganizationType.setString(1, flyOrganization.getType().toString());
            updateOrganizationOrganizationType.setInt(2, organizationId);
            if (updateOrganizationOrganizationType.executeUpdate() == 0) throw new SQLException();

            databaseManager.commit();
        } catch (SQLException exception) {
            System.out.println("An error occurred while executing a group of requests to update an object!");
            databaseManager.rollback();
            throw new DatabaseException();
        } finally {
            databaseManager.closePreparedStatement(updateOrganizationAddress);
            databaseManager.closePreparedStatement(updateOrganizationAnnualTurnover);
            databaseManager.closePreparedStatement(updateOrganizationCoordinates);
            databaseManager.closePreparedStatement(updateOrganizationName);
            databaseManager.closePreparedStatement(updateOrganizationEmployeesCount);
            databaseManager.closePreparedStatement(updateOrganizationFullName);
            databaseManager.closePreparedStatement(updateOrganizationOrganizationType);
            databaseManager.setAutoCommit();
        }
    }


    public void deleteOrganizationById(int organizationID) throws DatabaseException {
        PreparedStatement deleteOrganization = null;
        PreparedStatement deleteCoordinates = null;
        PreparedStatement deleteAddress = null;
        try {
            int coordinatesID = getCoordinatesIdByOrganizationID(organizationID);
            int addressId = getAddressIdByOrganizationID(organizationID);

            deleteOrganization = databaseManager.doPreparedStatement(DELETE_ORGANIZATION_BY_ID, false);
            deleteOrganization.setInt(1, organizationID);
            if (deleteOrganization.executeUpdate() == 0) {
                throw new SQLException();}
            deleteCoordinates = databaseManager.doPreparedStatement(DELETE_COORDINATES_BY_ID, false);
            deleteCoordinates.setInt(1, coordinatesID);
            if (deleteCoordinates.executeUpdate() == 0) {
                throw new SQLException();}

            deleteAddress = databaseManager.doPreparedStatement(DELETE_ADDRESS_BY_ID,  false);
            deleteAddress.setInt(1, addressId);
            if (deleteAddress.executeUpdate() == 0) {
                throw new SQLException();
            }

        } catch (SQLException exception) {
            exception.getStackTrace();
            System.out.println("An error occurred while executing the DELETE_GROUP_BY_ID request!");
            throw new DatabaseException();
        } finally {
            databaseManager.closePreparedStatement(deleteOrganization);
            databaseManager.closePreparedStatement(deleteCoordinates);
            databaseManager.closePreparedStatement(deleteAddress);
        }
    }

    public void clearCollection(ArrayList<Integer> idOrganizations) throws DatabaseException{
        Map<Integer, Organization> organizations = getCollection();
        for (Organization organization : organizations.values()) {
            if (idOrganizations.contains(organization.getId())) {
                deleteOrganizationById(organization.getId());
            }
        }
    }
}

