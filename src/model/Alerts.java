package model;

import javafx.scene.control.Alert;

public class Alerts {

    public static void errorBlank(String label){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String errorTitle = "Error";
        String errorMsg = label + " is blank";
        alert.setTitle(errorTitle);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
}
