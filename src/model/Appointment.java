package model;

import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;

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

    public Appointment(int appointmentId, Timestamp startDate){
        this.appointmentId = appointmentId;
        this.startDate = startDate;
    }

    public Appointment(int appointmentId, int customerId, Timestamp startDate, Timestamp endDate) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
