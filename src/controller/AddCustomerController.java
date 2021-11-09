package controller;

import DBConnect.DBGetStates;
import DBConnect.DBQuery;
import DBConnect.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private ComboBox<String> stateComboBox;


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

        /*
        Connection connection = JDBC.getConnection();
        try {
            DBQuery.setStatement(connection);
            Statement statement = DBQuery.getStatement();
            String selectStatement ="SELECT Division FROM first_level_divisions";
            statement.execute(selectStatement);
            ResultSet rsStates = statement.getResultSet();
            ObservableList<String> states = FXCollections.observableArrayList();
            while(rsStates.next()){
                String state = rsStates.getString("Division");
                states.add(state);
            }
            stateComboBox.setItems(states);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


         */
        stateComboBox.setItems(DBGetStates.getStates());
    }
}