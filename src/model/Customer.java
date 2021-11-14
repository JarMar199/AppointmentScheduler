package model;

/**
 * Creates a customer
 */
public class Customer {
    int id;
    String name, address, postal, phone, state, country;

    public Customer(int id, String name, String address, String postal, String phone, String state, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.state = state;
        this.country = country;
    }

    /**
     * @return the customer id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the customer name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the postal code
     */
    public String getPostal() {
        return postal;
    }

    /**
     * @return the phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the state or province
     */
    public String getState() {
        return state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

}
