package DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQueryPreparedStatement {

    private static PreparedStatement statement; //Statement reference

    //Create Statement Object
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        statement = connection.prepareStatement(sqlStatement);
    }

    //Return Statement Object
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }
}
