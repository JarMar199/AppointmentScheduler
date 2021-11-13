package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Creates an appointment object
 */
public class Appointment {
    int appointmentId, customerId, userId;
    String title, description, location, type, contactName;
    Timestamp startDate, endDate;


    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, String type, Timestamp startDate, Timestamp endDate, String contactName) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contactName = contactName;
    }

    /**
     * Constructor for user appointment data
     */
    public Appointment(int appointmentId, Timestamp startDate){
        this.appointmentId = appointmentId;
        this.startDate = startDate;
    }

    /**
     * Constructor for appointment conflicts
     */
    public Appointment(int appointmentId, int customerId, Timestamp startDate, Timestamp endDate) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructor for contact schedule appointment data
     */
    public Appointment(int appointmentId, int customerId, String title, String description, String type, Timestamp startDate, Timestamp endDate) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the appointment id
     */
    public int getAppointmentId() {
        return appointmentId;
    }



    /**
     * @return the customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the start date and time
     */
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * @return the end date and time
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

}
