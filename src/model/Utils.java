package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Helper methods
 */
public class Utils {

    /**
     * @return the list of available types
     */
    public static ObservableList<String> getTypes() {
        return FXCollections.observableArrayList("Meeting", "Lunch", "Coffee Break", "Review");
    }

    /**
     * @return the list of months
     */
    public static ObservableList<String> getMonths() {
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        return months;
    }

    /**
     * Records successful login to file
     *
     * @param user          username entered
     * @param localDateTime date and time on local machine
     * @throws IOException exception
     */
    public static void loginAttemptSuccessful(String user, LocalDateTime localDateTime) throws IOException {
        String filename = "src/login_activity.txt", log;
        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        log = "User " + user + " logged in at " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " from " + ZoneId.systemDefault().getId();
        outputFile.println((log));
        outputFile.close();
    }

    /**
     * Records unsuccessful login attempt to file
     *
     * @param user          username entered
     * @param localDateTime date and time on local machine
     * @throws IOException exception
     */
    public static void loginAttemptFail(String user, LocalDateTime localDateTime) throws IOException {
        String filename = "src/login_activity.txt", log;
        FileWriter fileWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fileWriter);
        log = "User " + user + " unsuccessful login attempt at " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " from " + ZoneId.systemDefault().getId();
        outputFile.println((log));
        outputFile.close();
    }
}
