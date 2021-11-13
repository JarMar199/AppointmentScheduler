package controller;

import DBConnect.DBQuery;
import javafx.collections.FXCollections;
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
import model.Alerts;
import model.Appointment;
import model.StartEndTime;
import model.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modifies an existing appointment in database
 */
public class ModifyAppointmentController implements Initializable{

    @FXML
    private Label apptIdLbl;

    @FXML
    private Label contactLbl;

    @FXML
    private Label customerIdLbl;

    @FXML
    private Label descriptionLbl;

    @FXML
    private Label endTimeLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label startDateLbl;

    @FXML
    private Label startTimeLbl;

    @FXML
    private Label titleLbl;

    @FXML
    private Label typeLbl;

    @FXML
    private Label userIdLbl;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private ComboBox<String> customerComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private ComboBox<LocalTime> endTimeComboBox;

    @FXML
    private TextField locationTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<LocalTime> startTimeComboBox;

    @FXML
    private TextField titleTxt;


    @FXML
    private ComboBox<String> userComboBox;

    /**
     * @param event Saves updated appointment information
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException, IOException {

        if(titleTxt.getText().isEmpty())
            Alerts.errorBlank(titleLbl.getText());
        else if (descriptionTxt.getText().isEmpty())
            Alerts.errorBlank(descriptionLbl.getText());
        else if (locationTxt.getText().isEmpty())
            Alerts.errorBlank(locationLbl.getText());
        else if (typeComboBox.getSelectionModel().isEmpty())
            Alerts.errorBlank(typeLbl.getText());
        else if (contactComboBox.getSelectionModel().isEmpty())
            Alerts.errorBlank((contactLbl.getText()));
        else if (customerComboBox.getSelectionModel().isEmpty())
            Alerts.errorBlank(customerIdLbl.getText());
        else if (userComboBox.getSelectionModel().isEmpty())
            Alerts.errorBlank((userIdLbl.getText()));
        else if (startDatePicker.getValue()  == null)
            Alerts.errorBlank(startDateLbl.getText());
        else if (startTimeComboBox.getSelectionModel().isEmpty())
            Alerts.errorBlank((startTimeLbl.getText()));
        else if (endTimeComboBox.getSelectionModel().isEmpty())
            Alerts.errorBlank((endTimeLbl.getText()));
        else {
            String title = titleTxt.getText();
            String description = descriptionTxt.getText();
            String location = locationTxt.getText();
            String type = typeComboBox.getSelectionModel().getSelectedItem();
            String contactName = contactComboBox.getSelectionModel().getSelectedItem();
            String customerId = customerComboBox.getSelectionModel().getSelectedItem();
            String userId = userComboBox.getSelectionModel().getSelectedItem();
            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = startTimeComboBox.getSelectionModel().getSelectedItem();
            LocalDateTime startDT = LocalDateTime.of(startDate, startTime);
            Timestamp startTimestamp = Timestamp.valueOf(startDT);
            Timestamp startDateTimeUTC = Timestamp.valueOf(StartEndTime.localToUTCConversion(startDT));
            LocalTime endTime = endTimeComboBox.getSelectionModel().getSelectedItem();
            LocalDateTime endDT = LocalDateTime.of(startDate, endTime);
            Timestamp endTimestamp = Timestamp.valueOf(endDT);
            Timestamp endDateTime = Timestamp.valueOf(StartEndTime.localToUTCConversion(endDT));
            String appointmentId = appointmentIdTxt.getText();

            if (startDT.isAfter(endDT) || startDT.isEqual(endDT)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String errorTitle = "Error";
                String errorMsg = "Start time must be before end time";
                alert.setTitle(errorTitle);
                alert.setContentText(errorMsg);
                alert.showAndWait();
                return;
            }
            if(startDate.isBefore(LocalDate.now())){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String errorTitle = "Error";
                String errorMsg = "Entered date must be today or in future";
                alert.setTitle(errorTitle);
                alert.setContentText(errorMsg);
                alert.showAndWait();
                return;
            }

            LocalTime estStartDT = StartEndTime.localToEST(startDT).toLocalTime();
            LocalTime estEndDT = StartEndTime.localToEST(endDT).toLocalTime();
            LocalTime estOpening = LocalTime.of(8, 0);
            LocalTime estClosing = LocalTime.of(22, 0);

            if (estStartDT.isBefore(estOpening) || estStartDT.isAfter(estClosing) || estEndDT.isBefore(estOpening) || estEndDT.isAfter(estClosing)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String errorTitle = "Error";
                String errorMsg = "Please select times during business hours";
                alert.setTitle(errorTitle);
                alert.setContentText(errorMsg);
                alert.showAndWait();
                return;
            }

            if(DBQuery.checkConflictsModify(customerId,startTimestamp, endTimestamp, appointmentId)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String errorTitle = "Error";
                String errorMsg = "Schedule conflict. Please enter new times";
                alert.setTitle(errorTitle);
                alert.setContentText(errorMsg);
                alert.showAndWait();
                return;
            }


            if (DBQuery.modifyAppointment(title, description, location, contactName, type, startDateTimeUTC, endDateTime, customerId, userId, appointmentId)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Confirmation");
                alert.setContentText("Appointment successfully saved");
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
                System.out.println("Success");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String errorTitle = "Error";
                String errorMsg = "There was an error saving appointment";
                alert.setTitle(errorTitle);
                alert.setContentText(errorMsg);
                alert.showAndWait();
            }
        }
    }

    /**
     * @param event returns user to main menu screen
     */
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

    /**
     * @param appointment fills information for appointment to be modified.
     *                    Selected appointment information is populated to form
     */
    public void sendAppointment(Appointment appointment) {
        appointmentIdTxt.setText(String.valueOf(appointment.getAppointmentId()));
        titleTxt.setText(appointment.getTitle());
        descriptionTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        typeComboBox.setValue(appointment.getType());
        contactComboBox.setValue(appointment.getContactName());
        startDatePicker.setValue(appointment.getStartDate().toLocalDateTime().toLocalDate());
        startTimeComboBox.setValue(LocalTime.from(appointment.getStartDate().toLocalDateTime()));
        endTimeComboBox.setValue(LocalTime.from(appointment.getEndDate().toLocalDateTime()));
        customerComboBox.setValue(String.valueOf(appointment.getCustomerId()));
        userComboBox.setValue(String.valueOf(appointment.getUserId()));

    }

    /**
     * retrieves contact, customer. user, and times for user selection
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactComboBox.setItems(DBQuery.getContacts());
            customerComboBox.setItems(DBQuery.getCustomerIds());
            userComboBox.setItems(DBQuery.getUserId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        startTimeComboBox.setItems(StartEndTime.getTimes());
        endTimeComboBox.setItems(StartEndTime.getTimes());
        typeComboBox.setItems(Utils.getTypes());
    }


}
