package controller;

import DBConnect.DBQuery;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label titleLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label locationTxt;

    @FXML
    private Button loginBtn;

    @FXML
    private Label passwordLbl;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField userNameTxt;

    @FXML
    void onActionQueryTest(ActionEvent event) throws SQLException {
        Timestamp localTime = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
       if( DBQuery.addAppointment("Coffee",
                "Meeting",
                "Sbux",
                "Li Lee",
                "planning",
                localTime,
                localTime,
                "4",
                "1")) {
           System.out.println("Success");
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
                DBQuery.setUserName(User_name);
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
        String errorTitle = "Error";
        String errorMsg = "Incorrect User Name or Password";
        if(Locale.getDefault().equals(Locale.FRANCE)) {
            ResourceBundle resource = ResourceBundle.getBundle("Login_fr");
            errorMsg = resource.getString("ErrorMsg");
            errorTitle = resource.getString("ErrorTitle");
        }
        alert.setTitle(errorTitle);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        locationTxt.setText(ZoneId.systemDefault().getId());
        Locale locale = Locale.getDefault();
       if(locale.equals(Locale.FRANCE)) {
           ResourceBundle resource = ResourceBundle.getBundle("Login_fr");
           String login = resource.getString("Login");
           String userName = resource.getString("Username");
           String password = resource.getString("Password");
           String title = resource.getString("Title");
           String location = resource.getString("Location");
           loginBtn.setText(login);
           usernameLbl.setText(userName);
           passwordLbl.setText(password);
           titleLbl.setText(title);
           locationLbl.setText(location);
       }
    }

}
