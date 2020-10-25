package dto;

import java.util.Date;

public class Customer {
    private final int id;
    private final String firstname, lastname;
    private final Date birthDate;
    private final String phoneNumber;

    public Customer(int id, String firstname, String lastname, Date birthDate, String phoneNumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
