package controller;

import DBConnect.DBQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Login screen that requires Username and Password to use application
 */

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

    /**
     * @param event Attempts login with input credentials.
     *              Validates username and password. Displays error message if unsuccessful. All login attempts are recorded.
     */
    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        String enteredUserName = userNameTxt.getText().trim();
        String enteredPassword = passwordTxt.getText().trim();
        LocalDateTime localDateTime = LocalDateTime.now();
        ResultSet rs = DBQuery.getLogin();

        while (rs.next()) {
            String User_name = rs.getString("User_name");
            String Password = rs.getString("Password");

            if (enteredUserName.equals(User_name) && enteredPassword.equals(Password)) {
                DBQuery.setUserName(User_name);
                Utils.loginAttemptSuccessful(enteredUserName, localDateTime);

                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
                ObservableList<Appointment> userAppts = DBQuery.getUserAppts(enteredUserName);
                LocalTime currentTime = LocalTime.now();

                for (Appointment appointment : userAppts) {
                    LocalTime startTime = appointment.getStartDate().toLocalDateTime().toLocalTime();
                    long timeDifference = ChronoUnit.MINUTES.between(currentTime, startTime);
                    if (timeDifference > 0 && timeDifference <= 15) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Upcoming");
                        String alertMsg = "Appointment: " + appointment.getAppointmentId() + " Starts at: " + appointment.getStartDate();
                        alert.setContentText(alertMsg);
                        alert.showAndWait();
                        return;
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Upcoming");
                String alertMsg = "No upcoming appointments";
                alert.setHeaderText("Information");
                alert.setContentText(alertMsg);
                alert.showAndWait();
                return;

            }
        }
        Utils.loginAttemptFail(enteredUserName, localDateTime);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String errorTitle = "Error";
        String errorMsg = "Incorrect User Name or Password";
        if (Locale.getDefault().equals(Locale.FRANCE)) {
            ResourceBundle resource = ResourceBundle.getBundle("Login_fr");
            errorMsg = resource.getString("ErrorMsg");
            errorTitle = resource.getString("ErrorTitle");
        }
        alert.setTitle(errorTitle);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }


    /**
     * Displays login screen in English or French
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        locationTxt.setText(ZoneId.systemDefault().getId());
        Locale locale = Locale.getDefault();
        if (locale.equals(Locale.FRANCE)) {
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
