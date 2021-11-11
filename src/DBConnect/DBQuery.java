package DBConnect;

import javafx.beans.binding.ObjectExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DBQuery {

    private static final Connection connection = DBConnection.getConnection();
    private static PreparedStatement statement; //Statement reference
    private static String userName;




    //Create Statement Object
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    //Return Statement Object
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }


    public static ObservableList<String> getStates(String country) throws SQLException {
        ObservableList<String> states = FXCollections.observableArrayList();
        String selectStatement ="SELECT Division FROM first_level_divisions WHERE Country_ID = (SELECT Country_ID FROM countries WHERE Country = ?)";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, country);
        ps.execute();
        ResultSet rsStates = ps.getResultSet();
        while(rsStates.next()){
            String state = rsStates.getString("Division");
            states.add(state);
        }
        return states;
    }

    public static ObservableList<String> getCountries() throws SQLException {
        ObservableList<String> countries = FXCollections.observableArrayList();
        String selectStatement ="SELECT Country FROM countries";
        DBQuery.setPreparedStatement(connection,selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsCountries = ps.getResultSet();
        while(rsCountries.next()){
            String country = rsCountries.getString("Country");
            countries.add(country);
        }
        return countries;
    }

    public static ResultSet getLogin() throws SQLException {
        String selectStatement ="SELECT User_Name, Password FROM users";
        DBQuery.setPreparedStatement(connection,selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        return ps.getResultSet();
    }

    public static void setUserName(String loggedInUser) {
        userName = loggedInUser;
    }

    public static String getUserName() {
        return userName;
    }

    public static boolean addCustomer(String name, String address, String postal, String phone, String state) throws SQLException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String user = DBQuery.getUserName();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(date,time));
        String divisionId = DBQuery.getDivisionId(state);

        String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID)\n" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        DBQuery.setPreparedStatement(connection,insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1,name);
        ps.setString(2,address);
        ps.setString(3,postal);
        ps.setString(4,phone);
        ps.setString(5, String.valueOf(timestamp));
        ps.setString(6,user);
        ps.setString(7, String.valueOf(timestamp));
        ps.setString(8,user);
        ps.setString(9, divisionId);

        ps.execute();
        return ps.getUpdateCount() > 0;
    }

    public static boolean modifyCustomer(int customerId, String name, String address, String postal, String phone, String state) throws SQLException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String user = DBQuery.getUserName();
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(date,time));
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
        DBQuery.setPreparedStatement(connection,updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1,name);
        ps.setString(2,address);
        ps.setString(3,postal);
        ps.setString(4,phone);
        ps.setString(5, String.valueOf(timestamp));
        ps.setString(6,user);
        ps.setInt(7, Integer.parseInt(divisionId));
        ps.setInt(8, customerId);

        ps.execute();
        return ps.getUpdateCount() > 0;
    }

    public static boolean addAppointment(String title, String description, String location, String contactName,
                                         String type, Timestamp startDT, Timestamp endDT, String customerId, String userId) throws SQLException {
        Timestamp localTime = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(),LocalTime.now()));
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

        DBQuery.setPreparedStatement(connection,insertStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setTimestamp(5, startDT);
        ps.setTimestamp(6, endDT);
        ps.setTimestamp(7, localTime);
        ps.setString(8,user);
        ps.setTimestamp(9, localTime);
        ps.setString(10,user);
        ps.setString(11,customerId);
        ps.setString(12,userId);
        ps.setString(13,contactId);

        ps.execute();
        return ps.getUpdateCount() > 0;
    }


    public static String getDivisionId(String division) throws SQLException {
        String divisionId = null;
        String selectStatement = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        DBQuery.setPreparedStatement(connection,selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, division);
        ps.execute();
        ResultSet rsDivisionId = ps.getResultSet();
        while (rsDivisionId.next()) {
            divisionId = rsDivisionId.getString("Division_ID");
        }

        return divisionId;
    }

    public static ObservableList<Customer> getCustomerTable() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String selectStatement = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone,first_level_divisions.Division AS 'State/Province' , countries.Country\n" +
                "FROM customers\n" +
                "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID\n" +
                "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID\n" +
                "GROUP BY Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division, Country";
        DBQuery.setPreparedStatement(connection,selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsCustomers = ps.getResultSet();
        while(rsCustomers.next()) {
            int customerId = rsCustomers.getInt("Customer_ID");
            String name = rsCustomers.getString("Customer_Name");
            String address = rsCustomers.getString("Address");
            String postal = rsCustomers.getString("Postal_Code");
            String phone = rsCustomers.getString("phone");
            String state = rsCustomers.getString("State/Province");
            String country = rsCustomers.getString("Country");
            customers.add(new Customer(customerId,name,address,postal,phone, state,country));
        }
        return customers;
    }

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
        DBQuery.setPreparedStatement(connection,selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsAppointments = ps.getResultSet();
        while(rsAppointments.next()) {
            int appointmentId = rsAppointments.getInt("Appointment_ID");
            String title = rsAppointments.getString("Title");
            String description = rsAppointments.getString("Description");
            String location = rsAppointments.getString("Location");
            String contactName = rsAppointments.getString("Contact_Name");
            String type = rsAppointments.getString("Type");
            Timestamp startDate = Timestamp.valueOf(rsAppointments.getString("Start"));
            Timestamp endDate = Timestamp.valueOf(rsAppointments.getString("End"));
            int customerId = rsAppointments.getInt("Customer_ID");
            int userId = rsAppointments.getInt("User_ID");
            appointments.add(new Appointment(appointmentId,customerId, userId, title, description, location, type, startDate, endDate, contactName));
        }

        return appointments;
    }

    public static ObservableList<String> getContacts() throws SQLException {
        ObservableList<String> contacts = FXCollections.observableArrayList();
        String selectStatement = "SELECT Contact_Name FROM contacts";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rsContacts = ps.getResultSet();
        while(rsContacts.next()) {
            String contact = rsContacts.getString("Contact_Name");
            contacts.add(contact);
        }
        return contacts;
    }

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

    public static String getContactId(String contact) throws SQLException {
        String contactId = null;
        String selectStatement = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
        DBQuery.setPreparedStatement(connection,selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, contact);
        ps.execute();
        ResultSet rsDivisionId = ps.getResultSet();
        while (rsDivisionId.next()) {
            contactId = rsDivisionId.getString("Contact_ID");
        }

        return contactId;
    }

}
