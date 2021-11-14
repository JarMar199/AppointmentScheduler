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
import model.Alerts;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modify an existing customer in database
 */
public class ModifyCustomerController implements Initializable {
    @FXML
    private Label addressLbl;

    @FXML
    private Label countryLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private Label phoneLbl;

    @FXML
    private Label postalLbl;

    @FXML
    private Label stateLbl;

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

    /**
     * @param event returns user to Main Menu screen
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
     * @param event inputs country values to Country field from database
     */
    @FXML
    void setCountryComboBox(ActionEvent event) throws SQLException {
        String selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        stateComboBox.setItems(DBQuery.getStates(selectedCountry));
    }

    /**
     * @param customer Populates customer information to be modified
     * @throws SQLException SQL exception
     */
    public void sendCustomer(Customer customer) throws SQLException {
        customerIdTxt.setText(String.valueOf(customer.getId()));
        nameTxt.setText(customer.getName());
        addressTxt.setText(customer.getAddress());
        postalTxt.setText(customer.getPostal());
        phoneTxt.setText(customer.getPhone());
        countryComboBox.setValue(customer.getCountry());
        String selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        stateComboBox.setItems(DBQuery.getStates(selectedCountry));
        stateComboBox.setValue(customer.getState());
    }

    /**
     * @param event saves updated customer information to database
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException, IOException {
        int customerId = Integer.parseInt(customerIdTxt.getText());
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String postal = postalTxt.getText();
        String phone = phoneTxt.getText();
        String state = stateComboBox.getSelectionModel().getSelectedItem();

        if (name.isEmpty())
            Alerts.errorBlank(nameLbl.getText());
        else if (address.isEmpty())
            Alerts.errorBlank(addressLbl.getText());
        else if (postal.isEmpty())
            Alerts.errorBlank(postalLbl.getText());
        else if (phone.isEmpty())
            Alerts.errorBlank(phoneLbl.getText());
        else if (state == null)
            Alerts.errorBlank(stateLbl.getText());
        else {
            if (DBQuery.modifyCustomer(customerId, name, address, postal, phone, state)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Confirmation");
                alert.setContentText("Customer successfully saved");
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
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
     * retrieves available countries for users to choose
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            countryComboBox.setItems(DBQuery.getCountries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
