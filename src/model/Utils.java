package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utils {

    public static ObservableList<String> getTypes() {
        ObservableList<String> types = FXCollections.observableArrayList("Meeting", "Lunch", "Coffee Break", "Review");
        return types;
    }

    public static ObservableList<String> getMonths(){
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        return months;
    }
}
