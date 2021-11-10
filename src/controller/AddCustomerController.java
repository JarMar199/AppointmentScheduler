package controller;

import DBConnect.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    private TextField addressTxt;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalTxt;

    @FXML
    private ComboBox<String> stateComboBox;

    @FXML
    private ComboBox<String> countryComboBox;


    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void setCountryComboBox(ActionEvent event) throws SQLException {
        String selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        stateComboBox.setItems(DBQuery.getStates(selectedCountry));
        System.out.println(selectedCountry);
        LocalDate date = LocalDate.now();
        System.out.println(date);
        LocalTime time = LocalTime.now();
        System.out.println(time);
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);




    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) {
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postal = postalTxt.getText();
        String phone = phoneTxt.getText();
        String state = stateComboBox.getSelectionModel().getSelectedItem();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            countryComboBox.setItems(DBQuery.getCountries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
