package model;

import DBConnect.DBGetCountries;
import DBConnect.DBGetStates;
import DBConnect.DBQuery;
import DBConnect.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        Connection connection = JDBC.getConnection();
        DBQuery.setStatement(connection);
        DBGetCountries.updateCountries();
        launch(args);
        JDBC.closeConnection();

    }
}
