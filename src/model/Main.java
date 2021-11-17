package model;

import DBConnect.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Creates an appointment and management application that uses a database
 */
public class Main extends Application {

    public static void main(String[] args) throws SQLException {
//        Locale.setDefault(new Locale("FR","fr"));
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        String loginTitle = "Login";
        if (Locale.getDefault().equals(Locale.FRANCE)) {
            ResourceBundle resource = ResourceBundle.getBundle("Login_fr");
            loginTitle = resource.getString("Login");
        }
        primaryStage.setTitle(loginTitle);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
