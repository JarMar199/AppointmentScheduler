package model;

import java.sql.Timestamp;

/**
 * Creates an appointment object
 */
public class Appointment {
    int appointmentId, customerId, userId;
    String title, description, location, type, contactName, lastUpdateBy;
    Timestamp startDate, endDate, lastUpdate;


    public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, String type, Timestamp startDate, Timestamp endDate, String contactName, Timestamp lastUpdate, String lastUpdateBy) {
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
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Constructor for user appointment data
     *
     * @param appointmentId appointment id to set
     * @param startDate     start date and time to set
     */
    public Appointment(int appointmentId, Timestamp startDate) {
        this.appointmentId = appointmentId;
        this.startDate = startDate;
    }

    /**
     * Constructor for appointment conflicts
     *
     * @param appointmentId appointment id to set
     * @param customerId    customer id to set
     * @param startDate     start date and time to set
     * @param endDate       end date and tiem to set
     */
    public Appointment(int appointmentId, int customerId, Timestamp startDate, Timestamp endDate) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructor for contact schedule appointment data
     *
     * @param appointmentId appointment id to set
     * @param customerId    customer id to set
     * @param title         title to set
     * @param description   description to set
     * @param type          type to set
     * @param startDate     start date and time to set
     * @param endDate       end date and time to set
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
     * Constructor for User report
     *
     * @param appointmentId the appointment id to set
     * @param title         the title to set
     * @param description   the description to set
     * @param type          the type to set
     * @param lastUpdate    the last update date and time to set
     * @param updateBy      the updated by to set
     */
    public Appointment(int appointmentId, String title, String description, String type, Timestamp lastUpdate, String updateBy) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.type = type;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = updateBy;
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

    /**
     * @return the last updated by user
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * @return the last update date and time
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
}
