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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> addressCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, String> customerIdCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, String> postalCol;

    @FXML
    private TableColumn<Customer, String> stateCol;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustomerIdCol;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

    @FXML
    private DatePicker apptDatePickerFilter;

    @FXML
    private ToggleGroup viewApptTG;

    @FXML
    private RadioButton viewAllRbtn;

    @FXML
    private RadioButton viewMonthRBtn;

    @FXML
    private RadioButton viewWeekRBtn;


    /**
     *
     * @param event takes user to Add Customer display screen
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param event takes user to modify customer display with selected customer
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {
        if(customerTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            loader.load();

            ModifyCustomerController MCController = loader.getController();
            MCController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();

        }
    }

    /**
     *
     * @param event delete selected customer and associated appointments from database
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if(selectedCustomer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete customer? All appointments associated will be deleted.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBQuery.deleteCustomer(selectedCustomer.getId());
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Confirmation");
                successAlert.setHeaderText("Confirmation");
                successAlert.setContentText("Customer successfully deleted.");
                successAlert.showAndWait();
                setViewAllApptTbl();
                setCustomerTable();

            }
        }
    }

    /**
     *
     * @param event takes user to Add appointment display screen
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param event takes user to modify appointment screen with selected appointment
     */
    @FXML
    void onActionModifyAppt(ActionEvent event) throws IOException {
        if(appointmentTable.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();

            ModifyAppointmentController MAController = loader.getController();
            MAController.sendAppointment(appointmentTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Appointment");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     *
     * @param event delete selected appointment from database
     */
    @FXML
    void onActionDeleteAppt(ActionEvent event) throws SQLException {
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedAppointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cancel Appointment "+ selectedAppointment.getAppointmentId() + " Type: " + selectedAppointment.getType() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DBQuery.deleteAppointment(Integer.valueOf(selectedAppointment.getAppointmentId()));
                Alert alertConfirm = new Alert(Alert.AlertType.INFORMATION);
                alertConfirm.setTitle("Success");
                alertConfirm.setHeaderText("Confirmation");
                alertConfirm.setContentText("Appointment: " + selectedAppointment.getAppointmentId() + " Type: " + selectedAppointment.getType() + " cancelled." );
                alertConfirm.showAndWait();
                setViewAllApptTbl();
            }
        }
    }

    /**
     *
     * @param event returns user to Login screen
     */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            String loginTitle = "Login";
            if (Locale.getDefault().equals(Locale.FRANCE)) {
                ResourceBundle resource = ResourceBundle.getBundle("Login_fr");
                loginTitle = resource.getString("Login");
            }
            stage.setTitle(loginTitle);
            stage.setScene(scene);
            stage.show();
        }

    }


    /**
     *
     * display all appointments on tableview
     * @throws SQLException exception
     */

    public void setViewAllApptTbl() throws SQLException {
        appointmentTable.setItems(DBQuery.viewAllAppointmentTable());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     *
     * displays customer information on tableview
     * @throws SQLException SQL exception
     */
    public void setCustomerTable() throws SQLException {
        customerTable.setItems(DBQuery.getCustomerTable());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
    }

    /**
     *
     * @param event displays all appointments when radio button selected
     */
    @FXML
    void onActionViewAllAppt(ActionEvent event) throws SQLException {
        setViewAllApptTbl();
    }

    /**
     *
     * @param event displays appointments for selected month
     */
    @FXML
    void onActionViewMonthAppt(ActionEvent event) throws SQLException {
        LocalDate selectedCalendarMonth = apptDatePickerFilter.getValue();
        if(selectedCalendarMonth != null) {
            appointmentTable.setItems(DBQuery.viewMonthlyAppointmentTable(selectedCalendarMonth));
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }
    }

    /**
     *
     * @param event displays one week appointments starting from Sunday for selected week
     */
    @FXML
    void onActionViewWeekAppt(ActionEvent event) throws SQLException {
        LocalDate selectedCalendarMonth = apptDatePickerFilter.getValue();
        if(selectedCalendarMonth != null) {
            appointmentTable.setItems(DBQuery.viewWeeklyAppointmentTable(selectedCalendarMonth));
            appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }
    }

    /**
     *
     * @param event Displays either monthly or weekly appointments dependent on view selection
     */
    @FXML
    void onActionDateSelect(ActionEvent event) throws SQLException {
        if(viewMonthRBtn.isSelected()) {
            onActionViewMonthAppt(event);
        } else if(viewWeekRBtn.isSelected()) {
            onActionViewWeekAppt(event);
        }
    }

    /**
     *
     * @param event takes user to Reports display screen
     */
    @FXML
    void onActionReports(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * displays information for customer and appointment tables
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCustomerTable();
            setViewAllApptTbl();



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        viewAllRbtn.setSelected(true);
    }
}
