package DBConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.StartEndTime;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

/**
 * Database queries to get information
 */
public class DBQuery {

    private static final Connection connection = DBConnection.getConnection();
    private static PreparedStatement statement; //Statement reference
    private static String userName;


    /**
     * Generates prepared statement
     *
     * @param connection   the connected database
     * @param sqlStatement Creates a SQL prepared statement
     * @throws SQLException SQL exception
     */
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    /**
     * @return the statement
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

    /**
     * @param country Generates list of states based on country selection
     * @return the list of states/provinces
     * @throws SQLException SQL exception
     */
    public static ObservableList<String> getStates(String country) throws SQLException {
        ObservableList<String> states = FXCollections.observableArrayList();
        String selectStatement = "SELECT Division FROM first_level_divisions WHERE Country_ID = (SELECT Country_ID FROM countries WHERE Country = ?)";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, country);
        ps.execute();
        ResultSet rsStates = ps.getResultSet();
        while (rsStates.next()) {
            String state = rsStates.getString("Division");
            states.add(state);
        }
        return states;
    }

    /**
     * Query to get list of countries
     *
     * @return the list of  countries
     * @throws SQLException SQL exception
     */
    public static ObservableList<String> getCountries() throws SQLException {
        ObservableList<String> countries = FXCollections.observableArrayList();
        String selectStatement = "SELECT Country FROM countries";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsCountries = ps.getResultSet();
        while (rsCountries.next()) {
            String country = rsCountries.getString("Country");
            countries.add(country);
        }
        return countries;
    }

    /**
     * Query to get list of usernames and passwords
     *
     * @return the result set for usernames and passwords
     * @throws SQLException SQL exception
     */
    public static ResultSet getLogin() throws SQLException {
        String selectStatement = "SELECT User_Name, Password FROM users";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        return ps.getResultSet();
    }

    /**
     * @return the logged in user
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * Sets logged in user name
     *
     * @param loggedInUser the logged in user to set
     */
    public static void setUserName(String loggedInUser) {
        userName = loggedInUser;
    }

    /**
     * Adds customer to database
     *
     * @param name    name to enter
     * @param address address to enter
     * @param postal  postal code to enter
     * @param phone   phone to enter
     * @param state   state to enter
     * @return Adds customer information to database
     * @throws SQLException SQL exception
     */
    public static boolean addCustomer(String name, String address, String postal, String phone, String state) throws SQLException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String user = DBQuery.getUserName();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(date, time));
        String divisionId = DBQuery.getDivisionId(state);

        String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)\n" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setTimestamp(5, timestamp);
        ps.setString(6, user);
        ps.setTimestamp(7, timestamp);
        ps.setString(8, user);
        ps.setString(9, divisionId);

        ps.execute();
        return ps.getUpdateCount() > 0;
    }

    /**
     * Query to Update customer information in the database
     *
     * @param customerId customer selected to modify
     * @param name       name to enter
     * @param address    address to enter
     * @param postal     postal code to enter
     * @param phone      phone to enter
     * @param state      state to enter
     * @return Updates customer information to database
     * @throws SQLException SQL exception
     */
    public static boolean modifyCustomer(int customerId, String name, String address, String postal, String phone, String state) throws SQLException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String user = DBQuery.getUserName();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(date, time));
        String divisionId = DBQuery.getDivisionId(state);

        String updateStatement = "UPDATE customers SET \n" +
                "\tCustomer_Name = ?,\n" +
                "    Address = ?,\n" +
                "    Postal_Code = ?,\n" +
                "    Phone = ?,\n" +
                "    Last_Update = ?,\n" +
                "    Last_Updated_By = ?,\n" +
                "    Division_ID = ?\n" +
                "WHERE Customer_ID = ?";
        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setTimestamp(5, timestamp);
        ps.setString(6, user);
        ps.setInt(7, Integer.parseInt(divisionId));
        ps.setInt(8, customerId);

        ps.execute();
        return ps.getUpdateCount() > 0;
    }

    /**
     * Query to delete customer from database
     *
     * @param customerId the selected customer to delete
     * @return Deletes customer and associated appointments from database
     * @throws SQLException SQL exception
     */
    public static boolean deleteCustomer(int customerId) throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Customer_ID = ?";
        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, customerId);
        ps.execute();

        deleteStatement = "DELETE FROM customers WHERE Customer_ID = ?";
        DBQuery.setPreparedStatement(connection, deleteStatement);
        ps = DBQuery.getPreparedStatement();
        ps.setInt(1, customerId);
        ps.execute();
        return ps.getUpdateCount() > 0;
    }

    /**
     * Query to add appointment to database
     *
     * @param title       title to enter
     * @param description description to enter
     * @param location    location to enter
     * @param contactName contact name selected
     * @param type        type selected
     * @param startDT     start date and time to enter
     * @param endDT       end date and time to enter
     * @param customerId  customer id to select
     * @param userId      user id to select
     * @return Adds appointment to database
     * @throws SQLException SQL Exception
     */
    public static boolean addAppointment(String title, String description, String location, String contactName, String type, Timestamp startDT, Timestamp endDT, String customerId, String userId) throws SQLException {
        Timestamp localTimestamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        String user = DBQuery.getUserName();
        String contactId = DBQuery.getContactId(contactName);
        String insertStatement = "INSERT INTO appointments(\n" +
                "\tTitle,\n" +
                "\tDescription,\n" +
                "\tLocation,\n" +
                "\tType,\n" +
                "\tStart,\n" +
                "\tEnd,\n" +
                "\tCreate_Date,\n" +
                "\tCreated_By, \n" +
                "\tLast_Update,\n" +
                "\tLast_Updated_By,\n" +
                "\tCustomer_ID,\n" +
                "\tUser_ID,\n" +
                "\tContact_ID)\n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBQuery.setPreparedStatement(connection, insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startDT);
        ps.setTimestamp(6, endDT);
        ps.setTimestamp(7, localTimestamp);
        ps.setString(8, user);
        ps.setTimestamp(9, localTimestamp);
        ps.setString(10, user);
        ps.setString(11, customerId);
        ps.setString(12, userId);
        ps.setString(13, contactId);

        ps.execute();
        return ps.getUpdateCount() > 0;
    }

    /**
     * Query to modify appointment
     *
     * @param appointmentId appointment selected to modify
     * @param title         title to enter
     * @param description   description to enter
     * @param location      location to enter
     * @param contactName   contact name selected
     * @param type          type selected
     * @param startDT       start date and time to enter
     * @param endDT         end date and time to enter
     * @param customerId    customer id to select
     * @param userId        user id to select
     * @return Modifies existing appointment to database
     * @throws SQLException SQL Exception
     */
    public static boolean modifyAppointment(String title, String description, String location, String contactName,
                                            String type, Timestamp startDT, Timestamp endDT, String customerId, String userId, String appointmentId) throws SQLException {
        Timestamp localTimestamp = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        String user = DBQuery.getUserName();
        String contactId = DBQuery.getContactId(contactName);
        String updateStatement = "UPDATE appointments\n" +
                "SET \n" +
                "\tTitle = ?,\n" +
                "\tDescription = ?,\n" +
                "\tLocation = ?,\n" +
                "\tType = ?,\n" +
                "\tStart = ?,\n" +
                "\tEnd = ?,\n" +
                "\tLast_Update = ?,\n" +
                "\tLast_Updated_By = ?,\n" +
                "\tCustomer_ID = ?,\n" +
                "\tUser_ID = ?,\n" +
                "\tContact_ID = ?\n" +
                "WHERE Appointment_ID = ?";
        DBQuery.setPreparedStatement(connection, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startDT);
        ps.setTimestamp(6, endDT);
        ps.setTimestamp(7, localTimestamp);
        ps.setString(8, user);
        ps.setString(9, customerId);
        ps.setString(10, userId);
        ps.setString(11, contactId);
        ps.setString(12, appointmentId);


        ps.execute();
        return ps.getUpdateCount() > 0;

    }

    /**
     * Query to Delete an appointment from the database
     *
     * @param appointmentId appointment selected to delete
     * @return Deletes appointment from database
     * @throws SQLException SQL exception
     */
    public static boolean deleteAppointment(int appointmentId) throws SQLException {
        String deleteStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        DBQuery.setPreparedStatement(connection, deleteStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, appointmentId);
        ps.execute();
        return ps.getUpdateCount() > 0;
    }

    /**
     * @param division the division to search for the ID
     * @return the division ID
     * @throws SQLException SQL exception
     */
    public static String getDivisionId(String division) throws SQLException {
        String divisionId = null;
        String selectStatement = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, division);
        ps.execute();
        ResultSet rsDivisionId = ps.getResultSet();
        while (rsDivisionId.next()) {
            divisionId = rsDivisionId.getString("Division_ID");
        }

        return divisionId;
    }

    /**
     * @return list of customers that are in the database
     * @throws SQLException SQL exception
     */
    public static ObservableList<Customer> getCustomerTable() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String selectStatement = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone,first_level_divisions.Division AS 'State/Province' , countries.Country\n" +
                "FROM customers\n" +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID\n" +
                "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID\n" +
                "GROUP BY Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division, Country";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsCustomers = ps.getResultSet();
        while (rsCustomers.next()) {
            int customerId = rsCustomers.getInt("Customer_ID");
            String name = rsCustomers.getString("Customer_Name");
            String address = rsCustomers.getString("Address");
            String postal = rsCustomers.getString("Postal_Code");
            String phone = rsCustomers.getString("phone");
            String state = rsCustomers.getString("State/Province");
            String country = rsCustomers.getString("Country");
            customers.add(new Customer(customerId, name, address, postal, phone, state, country));
        }
        return customers;
    }

    /**
     * @return list of all appointments in the database
     * @throws SQLException SQL exception
     */
    public static ObservableList<Appointment> viewAllAppointmentTable() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String selectStatement = "SELECT \n" +
                "\tappointments.Appointment_ID, \n" +
                "\tappointments.Title, \n" +
                "\tappointments.Description,\n" +
                "\tappointments.Location,\n" +
                "    contacts.Contact_Name,\n" +
                "    appointments.Type,\n" +
                "\tappointments.Start,\n" +
                "\tappointments.end,\n" +
                "    appointments.Customer_ID,\n" +
                "\tappointments.User_ID\n" +
                "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "GROUP BY Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsAppointments = ps.getResultSet();

        while (rsAppointments.next()) {
            int appointmentId = rsAppointments.getInt("Appointment_ID");
            String title = rsAppointments.getString("Title");
            String description = rsAppointments.getString("Description");
            String location = rsAppointments.getString("Location");
            String contactName = rsAppointments.getString("Contact_Name");
            String type = rsAppointments.getString("Type");

            Timestamp startDate = rsAppointments.getTimestamp("Start");
            Timestamp localStartDate = Timestamp.valueOf(StartEndTime.utcToLocalConversion(startDate.toLocalDateTime()));
            Timestamp endDate = rsAppointments.getTimestamp("End");
            Timestamp localEndDate = Timestamp.valueOf(StartEndTime.utcToLocalConversion(endDate.toLocalDateTime()));

            int customerId = rsAppointments.getInt("Customer_ID");
            int userId = rsAppointments.getInt("User_ID");
            appointments.add(new Appointment(appointmentId, customerId, userId, title, description, location, type, startDate, endDate, contactName, null, null));
        }

        return appointments;
    }

    /**
     * View the selected month of appointments
     *
     * @param selectedMonth the month to filter with
     * @return list of filtered appointments with the selected month
     * @throws SQLException SQL exception
     */
    public static ObservableList<Appointment> viewMonthlyAppointmentTable(LocalDate selectedMonth) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String selectStatement = "SELECT \n" +
                "\tappointments.Appointment_ID, \n" +
                "\tappointments.Title, \n" +
                "\tappointments.Description,\n" +
                "\tappointments.Location,\n" +
                "    contacts.Contact_Name,\n" +
                "    appointments.Type,\n" +
                "\tappointments.Start,\n" +
                "\tappointments.end,\n" +
                "    appointments.Customer_ID,\n" +
                "\tappointments.User_ID\n" +
                "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE MONTH(Start) = ?\n" +
                "GROUP BY Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setInt(1, selectedMonth.getMonthValue());
        ps.execute();
        ResultSet rsAppointments = ps.getResultSet();

        while (rsAppointments.next()) {
            int appointmentId = rsAppointments.getInt("Appointment_ID");
            String title = rsAppointments.getString("Title");
            String description = rsAppointments.getString("Description");
            String location = rsAppointments.getString("Location");
            String contactName = rsAppointments.getString("Contact_Name");
            String type = rsAppointments.getString("Type");

            Timestamp startDate = rsAppointments.getTimestamp("Start");
            Timestamp localStartDate = Timestamp.valueOf(StartEndTime.utcToLocalConversion(startDate.toLocalDateTime()));
            Timestamp endDate = rsAppointments.getTimestamp("End");
            Timestamp localEndDate = Timestamp.valueOf(StartEndTime.utcToLocalConversion(endDate.toLocalDateTime()));

            int customerId = rsAppointments.getInt("Customer_ID");
            int userId = rsAppointments.getInt("User_ID");
            appointments.add(new Appointment(appointmentId, customerId, userId, title, description, location, type, startDate, endDate, contactName, null, null));
        }

        return appointments;
    }

    /**
     * Views one week of appointments from previous Sunday of selected date
     *
     * @param selectedMonth month selected to filter
     * @return list of filtered appointments for the selected week
     * @throws SQLException SQL exception
     */
    public static ObservableList<Appointment> viewWeeklyAppointmentTable(LocalDate selectedMonth) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String selectStatement = "SELECT \n" +
                "\tappointments.Appointment_ID, \n" +
                "\tappointments.Title, \n" +
                "\tappointments.Description,\n" +
                "\tappointments.Location,\n" +
                "    contacts.Contact_Name,\n" +
                "    appointments.Type,\n" +
                "\tappointments.Start,\n" +
                "\tappointments.end,\n" +
                "    appointments.Customer_ID,\n" +
                "\tappointments.User_ID\n" +
                "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE DATE(START) >= ? AND  DATE(START) < date_add(?, INTERVAL 7 DAY)\n" +
                "GROUP BY Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, Customer_ID, User_ID";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, String.valueOf(selectedMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))));
        ps.setString(2, String.valueOf(selectedMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))));
        ps.execute();
        ResultSet rsAppointments = ps.getResultSet();

        while (rsAppointments.next()) {
            int appointmentId = rsAppointments.getInt("Appointment_ID");
            String title = rsAppointments.getString("Title");
            String description = rsAppointments.getString("Description");
            String location = rsAppointments.getString("Location");
            String contactName = rsAppointments.getString("Contact_Name");
            String type = rsAppointments.getString("Type");

            Timestamp startDate =rsAppointments.getTimestamp("Start");
            Timestamp localStartDate = Timestamp.valueOf(StartEndTime.utcToLocalConversion(startDate.toLocalDateTime()));
            Timestamp endDate = rsAppointments.getTimestamp("End");
            Timestamp localEndDate = Timestamp.valueOf(StartEndTime.utcToLocalConversion(endDate.toLocalDateTime()));

            int customerId = rsAppointments.getInt("Customer_ID");
            int userId = rsAppointments.getInt("User_ID");
            appointments.add(new Appointment(appointmentId, customerId, userId, title, description, location, type, startDate, endDate, contactName, null, null));
        }

        return appointments;
    }

    /**
     * Query for contacts in database
     *
     * @return list of contacts in database
     * @throws SQLException SQL exception
     */
    public static ObservableList<String> getContacts() throws SQLException {
        ObservableList<String> contacts = FXCollections.observableArrayList();
        String selectStatement = "SELECT Contact_Name FROM contacts";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsContacts = ps.getResultSet();
        while (rsContacts.next()) {
            String contact = rsContacts.getString("Contact_Name");
            contacts.add(contact);
        }
        return contacts;
    }

    /**
     * Query for customer Ids in database
     *
     * @return list of customer IDs in database
     * @throws SQLException SQL exception
     */
    public static ObservableList<String> getCustomerIds() throws SQLException {
        ObservableList<String> customerIds = FXCollections.observableArrayList();
        String selectStatement = "SELECT Customer_ID from customers ORDER BY Customer_ID ASC";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsCustomerIds = ps.getResultSet();
        while (rsCustomerIds.next()) {
            String customerId = rsCustomerIds.getString("Customer_ID");
            customerIds.add(customerId);
        }
        return customerIds;
    }

    /**
     * Query for user Ids in database
     *
     * @return list of user IDs in database
     * @throws SQLException SQL exception
     */
    public static ObservableList<String> getUserId() throws SQLException {
        ObservableList<String> userIds = FXCollections.observableArrayList();
        String selectStatement = "SELECT User_ID from users ORDER BY User_ID ASC";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsUserIds = ps.getResultSet();
        while (rsUserIds.next()) {
            String userId = rsUserIds.getString("User_ID");
            userIds.add(userId);
        }
        return userIds;
    }

    /**
     * Query for contact Id from selected contact
     *
     * @param contact the contact to search the ID
     * @return the contact ID of inputted contact
     * @throws SQLException SQL exception
     */
    public static String getContactId(String contact) throws SQLException {
        String contactId = null;
        String selectStatement = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, contact);
        ps.execute();
        ResultSet rsDivisionId = ps.getResultSet();
        while (rsDivisionId.next()) {
            contactId = rsDivisionId.getString("Contact_ID");
        }

        return contactId;
    }

    /**
     * Query for appointment information for selected user
     *
     * @param userId search for associated appointments to userId
     * @return list of appointments for the inputted userId
     * @throws SQLException SQL exception
     */
    public static ObservableList<Appointment> getUserAppts(String userId) throws SQLException {
        ObservableList<Appointment> userAppts = FXCollections.observableArrayList();
        LocalDate localDate = LocalDate.now();
        String selectStatement = "SELECT appointment_ID, start FROM appointments\n" +
                "INNER JOIN users ON appointments.User_ID = users.User_ID\n" +
                "WHERE users.User_Name = ?\n" +
                "AND date( appointments.start) = ?";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, userId);
        ps.setString(2, String.valueOf(localDate));
        ps.execute();
        ResultSet rsUserAppts = ps.getResultSet();
        while (rsUserAppts.next()) {
            int appointmentId = rsUserAppts.getInt("appointment_ID");
            Timestamp start = rsUserAppts.getTimestamp("start");
            userAppts.add(new Appointment(appointmentId, start));
        }
        return userAppts;
    }

    /**
     * Validates appointment can be saved without overlaps
     *
     * @param customerId customer to search existing appointments
     * @param startTime  customer appointment start times
     * @param endTime    customer appointment end times
     * @return Validation if appointment can be scheduled without conflict
     * @throws SQLException SQL exception
     */
    public static boolean checkConflictsAdd(String customerId, Timestamp startTime, Timestamp endTime) throws SQLException {
        ObservableList<Appointment> customerAppts = FXCollections.observableArrayList();
        String selectStatement = "SELECT Appointment_ID, Start, End, Customer_ID FROM appointments WHERE Customer_ID = ?";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, customerId);
        ps.execute();
        ResultSet rsCustomerAppts = ps.getResultSet();
        while (rsCustomerAppts.next()) {
            int appointmentId = rsCustomerAppts.getInt("Appointment_ID");
            Timestamp apptLocalStart = rsCustomerAppts.getTimestamp("Start");
            Timestamp apptLocalEnd = rsCustomerAppts.getTimestamp("End");
            int apptCustomerId = rsCustomerAppts.getInt("Customer_ID");
            customerAppts.add(new Appointment(appointmentId, apptCustomerId, apptLocalStart, apptLocalEnd));
        }

        for (Appointment appointment : customerAppts) {
            Timestamp apptStart = appointment.getStartDate();
            Timestamp apptEnd = appointment.getEndDate();
            if ((startTime.equals(apptStart) || startTime.after(apptStart)) && startTime.before(apptEnd)) {
                return true;
            } else if (endTime.after(apptStart) && ((endTime.before(apptEnd) || endTime.equals(apptEnd)))) {
                return true;
            } else if ((startTime.before(apptStart) || startTime.equals(apptStart)) && (endTime.after(apptEnd) || endTime.equals(apptEnd))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates existing appointment can be saved without overlaps
     *
     * @param apptId     appointment select to be modified and excluded in appointment search
     * @param customerId customer to search existing appointments
     * @param startTime  customer appointment start times
     * @param endTime    customer appointment end times
     * @return Validation if appointment can be scheduled without conflict
     * @throws SQLException SQL exception
     */
    public static boolean checkConflictsModify(String customerId, Timestamp startTime, Timestamp endTime, String apptId) throws SQLException {
        ObservableList<Appointment> customerAppts = FXCollections.observableArrayList();
        String selectStatement = "SELECT Appointment_ID, Start, End, Customer_ID FROM appointments WHERE Customer_ID = ? AND Appointment_ID <> ?";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, customerId);
        ps.setString(2, apptId);
        ps.execute();
        ResultSet rsCustomerAppts = ps.getResultSet();
        while (rsCustomerAppts.next()) {
            int appointmentId = rsCustomerAppts.getInt("Appointment_ID");
            Timestamp apptLocalStart = rsCustomerAppts.getTimestamp("Start");
            Timestamp apptLocalEnd = rsCustomerAppts.getTimestamp("End");
            int apptCustomerId = rsCustomerAppts.getInt("Customer_ID");
            customerAppts.add(new Appointment(appointmentId, apptCustomerId, apptLocalStart, apptLocalEnd));
        }

        for (Appointment appointment : customerAppts) {
            Timestamp apptStart = appointment.getStartDate();
            Timestamp apptEnd = appointment.getEndDate();
            if ((startTime.equals(apptStart) || startTime.after(apptStart)) && startTime.before(apptEnd)) {
                return true;
            } else if (endTime.after(apptStart) && ((endTime.before(apptEnd) || endTime.equals(apptEnd)))) {
                return true;
            } else if ((startTime.before(apptStart) || startTime.equals(apptStart)) && (endTime.after(apptEnd) || endTime.equals(apptEnd))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Query for number of appointments for month and type
     *
     * @param month the month to search for
     * @param type  the type to search for
     * @return the number of appointments in search criteria
     * @throws SQLException SQL exception
     */
    public static String numOfAppts(String month, String type) throws SQLException {
        String selectStatement = "SELECT Count(*) AS Num_Appts FROM appointments\n" +
                "WHERE month(Start) = date_format(str_to_date(?,'%M'),'%c') AND TYPE = ?";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, month);
        ps.setString(2, type);
        ps.execute();
        String numAppt = "0";
        ResultSet rsNumAppts = ps.getResultSet();
        while (rsNumAppts.next())
            numAppt = rsNumAppts.getString("Num_Appts");
        return numAppt;
    }

    /**
     * Query for selected contact's scheduled appointments
     *
     * @param contact the contact to search
     * @return the appointment schedule for the selected contact
     * @throws SQLException SQL exception
     */
    public static ObservableList<Appointment> getSchedule(String contact) throws SQLException {
        String selectStatement = "SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments\n" +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID\n" +
                "WHERE Contact_Name = ?";
        ObservableList<Appointment> contactAppts = FXCollections.observableArrayList();
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, contact);
        ps.execute();
        ResultSet rsContactAppts = ps.getResultSet();
        while (rsContactAppts.next()) {
            int apptId = rsContactAppts.getInt("Appointment_ID");
            String title = rsContactAppts.getString("Title");
            String type = rsContactAppts.getString("Type");
            String description = rsContactAppts.getString("Description");
            Timestamp start = rsContactAppts.getTimestamp("Start");
            Timestamp end = rsContactAppts.getTimestamp("End");
            int customerId = rsContactAppts.getInt("Customer_ID");
            contactAppts.add(new Appointment(apptId, customerId, title, description, type, start, end));
        }
        return contactAppts;
    }

    /**
     * Query to get users from database
     *
     * @return List of users
     * @throws SQLException SQL exception
     */
    public static ObservableList<String> getUsers() throws SQLException {
        String selectStatement = "SELECT User_Name FROM users";
        ObservableList<String> users = FXCollections.observableArrayList();
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsUsers = ps.getResultSet();
        while (rsUsers.next()) {
            String userName = rsUsers.getString("User_Name");
            users.add(userName);
        }
        return users;
    }

    /**
     * Query to get appointments updated by user
     *
     * @param userName user to search
     * @return list of appointments of selected user
     * @throws SQLException SQL exception
     */
    public static ObservableList<Appointment> getLastUpdate(String userName) throws SQLException {
        String selectStatement = "SELECT Appointment_ID, Title, Description, Type, appointments.Last_Update, appointments.Last_Updated_By FROM appointments\n" +
                "INNER JOIN users ON appointments.User_ID = users.User_ID\n" +
                "WHERE appointments.Last_Updated_By = ?";

        ObservableList<Appointment> updateList = FXCollections.observableArrayList();
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, userName);
        ps.execute();
        ResultSet rsLastUpdate = ps.getResultSet();
        while (rsLastUpdate.next()) {
            int appointmentId = rsLastUpdate.getInt("Appointment_ID");
            String title = rsLastUpdate.getString("Title");
            String description = rsLastUpdate.getString("Description");
            String type = rsLastUpdate.getString("Type");
            Timestamp lastUpdate = rsLastUpdate.getTimestamp("Last_Update");
            String updateBy = rsLastUpdate.getString("Last_Updated_By");
            updateList.add(new Appointment(appointmentId, title, description, type, lastUpdate, updateBy));
        }

        return updateList;
    }

}
