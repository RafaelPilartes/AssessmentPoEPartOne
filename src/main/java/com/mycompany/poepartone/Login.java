package com.mycompany.poepartone;

public class Login {
    private String username;
    private String password;
    private String cellPhone;
    private String firstName;
    private String lastName;
    
    public Login(String username, String password, String cellPhone, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellPhone = cellPhone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }
    
    public boolean checkPasswordComplexity() {
        return password.length() >= 8 &&
            password.matches(".*[A-Z].*") &&
            password.matches(".*\\d.*") &&
            password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean checkCellPhoneNumber() {
        // Must start with +27 and be followed by exactly 9 digits (total 12 characters)
        return cellPhone.matches("\\+27\\d{9}");
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted international code.";
        }
        return "User successfully registered.";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }

    public String returnLoginStatus(String enteredUsername, String enteredPassword) {
        if (loginUser(enteredUsername, enteredPassword)) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }    
}
