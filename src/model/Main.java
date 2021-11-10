package model;

import DBConnect.DBQuery;
import DBConnect.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        String loginTitle = "Login";
        if(Locale.getDefault().equals(Locale.FRANCE)) {
            ResourceBundle resource = ResourceBundle.getBundle("Login_fr");
            loginTitle = resource.getString("Login");
        }
        primaryStage.setTitle(loginTitle);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        //Locale.setDefault(new Locale("FR","fr"));
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

    }
}
