package DBConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBGetStates {

    public static ObservableList<String> updateStates(String country) throws SQLException {

        ObservableList<String> states = FXCollections.observableArrayList();
        Statement statement = DBQuery.getStatement();
        String selectStatement ="SELECT Division FROM first_level_divisions WHERE Country_ID = (SELECT Country_ID FROM countries WHERE Country = " + "'" + country + "')";
        statement.execute(selectStatement);
        ResultSet rsStates = statement.getResultSet();
        while(rsStates.next()){
            String state = rsStates.getString("Division");
            states.add(state);
        }
        return states;
    }
}
