package DBConnect;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBGetStates {
    public static ObservableList<String> states = FXCollections.observableArrayList();

    public static ObservableList getStates(){
        return states;
    }

    public static void updateStates() throws SQLException {
        Connection connection = JDBC.getConnection();
        DBQuery.setStatement(connection);
        Statement statement = DBQuery.getStatement();
        String selectStatement ="SELECT Division FROM first_level_divisions";
        statement.execute(selectStatement);
        ResultSet rsStates = statement.getResultSet();
        while(rsStates.next()){
            String state = rsStates.getString("Division");
            states.add(state);
        }
    }
}
