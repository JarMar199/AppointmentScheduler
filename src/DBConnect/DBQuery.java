package DBConnect;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

    private static Statement statement; //Statement reference

    //Create Statement Object
    public static void setStatement(Connection connection) throws SQLException {
        statement = connection.createStatement();
    }

    //Return Statement Object
    public static Statement getStatement(){
        return statement;
    }
}
