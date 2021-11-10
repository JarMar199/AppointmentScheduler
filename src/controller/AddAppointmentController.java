package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class AddAppointmentController {

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private ComboBox<?> customerComboBox;

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
    private ComboBox<?> userComboBox;

    @FXML
    void onActionSaveAppointment(ActionEvent event) {

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
}
