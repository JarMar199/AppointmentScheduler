package controller;

import DBConnect.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable{

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private ComboBox<String> customerComboBox;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<?> endTimeComboBox;

    @FXML
    private TextField locationTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<?> startTimeComboBox;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private ComboBox<String> userComboBox;

    @FXML
    void onActionSaveAppointment(ActionEvent event) {
        String title = titleTxt.getText();
        String description = descriptionTxt.getText();
        String location = locationTxt.getText();
        String type = typeTxt.getText();
        String contact = contactComboBox.getSelectionModel().getSelectedItem();
        String customerId = customerComboBox.getSelectionModel().getSelectedItem();
        String userId = userComboBox.getSelectionModel().getSelectedItem();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactComboBox.setItems(DBQuery.getContacts());
            customerComboBox.setItems(DBQuery.getCustomerId());
            userComboBox.setItems(DBQuery.getUserId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
