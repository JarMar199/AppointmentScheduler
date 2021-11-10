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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    void onActionAddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

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

    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerTable.setItems(DBQuery.getCustomerTable());
            customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
            countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
