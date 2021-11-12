package controller;

import DBConnect.DBQuery;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Appointment;
import model.Utils;

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
    void onActionContactSelect(ActionEvent event) {

    }

    @FXML
    void onActionMonthSelect(ActionEvent event) {

    }

    @FXML
    void onActionTypeSelect(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contactComboBox.setItems(DBQuery.getContacts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        monthComboBox.setItems(Utils.getMonths());
        typeComboBox.setItems(Utils.getTypes());
    }
}
