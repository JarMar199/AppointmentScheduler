package DBConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBGetCountries {
    public static ObservableList<String> countries = FXCollections.observableArrayList();

    public static ObservableList getCountries(){
        return countries;
    }

    public static void updateCountries() throws SQLException {
        //Connection connection = JDBC.getConnection();
       // DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();
        String selectStatement ="SELECT Country FROM countries";
        statement.execute(selectStatement);
        ResultSet rsCountries = statement.getResultSet();
        while(rsCountries.next()){
            String country = rsCountries.getString("Country");
            countries.add(country);
        }
    }
}
