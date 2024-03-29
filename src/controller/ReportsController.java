package controller;

import DBConnect.DBQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Utils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Generates several reports for viewing
 */
public class ReportsController implements Initializable {

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private Label apptCountLbl;

    @FXML
    private TableColumn<Appointment, Integer> apptCustomerIdCol;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableView<Appointment> scheduleTable;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, Integer> updateApptIdCol;

    @FXML
    private TableColumn<Appointment, String> updateDescriptionCol;

    @FXML
    private TableView<Appointment> updateTable;

    @FXML
    private TableColumn<Appointment, String> updateTitleCol;

    @FXML
    private TableColumn<Appointment, String> updateTypeCol;

    @FXML
    private TableColumn<Appointment, String> updateLastCol;

    @FXML
    private TableColumn<Appointment, String> updatedByCol;

    @FXML
    private ComboBox<String> userComboBox;

    /**
     * Filters appointments by the user that updated the appontments last
     *
     * @param event displays appointments on table for selected user
     * @throws SQLException SQL exception
     */
    @FXML
    void onActionUserSelect(ActionEvent event) throws SQLException {
        String user = userComboBox.getSelectionModel().getSelectedItem();
        updateTable.setItems(DBQuery.getLastUpdate(user));
        updateApptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        updateTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        updateDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        updateTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        updateLastCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));

    }


    /**
     * @param event Displays selected contact's schedule
     */
    @FXML
    void onActionContactSelect(ActionEvent event) throws SQLException {
        String contact = contactComboBox.getSelectionModel().getSelectedItem();
        scheduleTable.setItems(DBQuery.getSchedule(contact));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    /**
     * @param event Displays number of customer appointments by month and type
     */
    @FXML
    void onActionMonthSelect(ActionEvent event) throws SQLException {
        String month = monthComboBox.getSelectionModel().getSelectedItem();
        String type = typeComboBox.getSelectionModel().getSelectedItem();

        if (!monthComboBox.getSelectionModel().isEmpty() && !typeComboBox.getSelectionModel().isEmpty()) {
            String numAppts = DBQuery.numOfAppts(month, type);
            apptCountLbl.setText(numAppts);
        }
    }

    /**
     * @param event Displays number of customer appointments by month and type
     */
    @FXML
    void onActionTypeSelect(ActionEvent event) throws SQLException {
        String month = monthComboBox.getSelectionModel().getSelectedItem();
        String type = typeComboBox.getSelectionModel().getSelectedItem();

        if (!monthComboBox.getSelectionModel().isEmpty() && !typeComboBox.getSelectionModel().isEmpty()) {
            String numAppts = DBQuery.numOfAppts(month, type);
            apptCountLbl.setText(numAppts);
        }
    }

    /**
     * @param event returns user to main menu
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * retrieves contacts, months, and types for user selection
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contactComboBox.setItems(DBQuery.getContacts());
            userComboBox.setItems(DBQuery.getUsers());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        monthComboBox.setItems(Utils.getMonths());
        typeComboBox.setItems(Utils.getTypes());

    }
}
