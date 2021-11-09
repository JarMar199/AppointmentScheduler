package controller;

import DBConnect.DBQuery;
import DBConnect.JDBC;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField userNameTxt;

    @FXML
    void loginEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

        }
    }

    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        String enteredUserName = userNameTxt.getText().trim();
        String enteredPassword = passwordTxt.getText().trim();
        ResultSet rs = DBQuery.getLogin();

        while (rs.next() && rs !=null) {
            String User_name = rs.getString("User_name");
            String Password = rs.getString("Password");

            if (enteredUserName.equals(User_name) && enteredPassword.equals(Password)){
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Incorrect User Name or Password");
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
