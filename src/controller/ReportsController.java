package controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReportsController implements Initializable {

    @FXML
    private TableColumn<?, ?> appointmentIdCol;

    @FXML
    private Label apptCountLbl;

    @FXML
    private TableColumn<?, ?> apptCustomerIdCol;

    @FXML
    private ComboBox<?> comboBoxContact;

    @FXML
    private ComboBox<?> comboBoxMonth;

    @FXML
    private ComboBox<?> comboBoxType;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TableColumn<?, ?> endCol;

    @FXML
    private TableView<?> scheduleTable;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TableColumn<?, ?> typeCol;

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

    }
}
