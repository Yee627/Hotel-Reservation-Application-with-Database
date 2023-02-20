package model;

import java.util.regex.Pattern;

public class Customer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public Customer(){

    }

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.validateEmail(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private final String emailRegex = "^(.+)@(.+).com$";

    private void validateEmail(String email) {
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error, Invalid Email!");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer Id: " + id + "\nFirst Name: " + firstName + "\nLast Name: " + lastName + "\nEmail: " + email + "\n";
    }
}
