package DBConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DBQuery {

    private static Connection connection = JDBC.getConnection();
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
        ResultSet rs = ps.getResultSet();
        return rs;
    }

    public static void setUserName(String loggedInUser) {
        userName = loggedInUser;
    }
    public static String getUserName() {
        return userName;
    }

    public static void addCustomer() {
        //String insertStatement = "INSERT INTO "
    }

}
