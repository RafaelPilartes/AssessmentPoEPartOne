package com.mycompany.poepartone;
import java.util.Scanner;

public class POEPartOne {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String firstName = "";
        String lastName = "";
        String username = "";
        String password = "";
        String phone = "";

        boolean isMatch;
        String messageFeedback;

        // === FIRST AND LAST NAME ===
        System.out.print("Enter your first name: ");
        firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        lastName = scanner.nextLine();

        // Creates new Login object
        Login user = new Login(firstName, lastName);

        System.out.println("\n=== Registration ===");
        // === USERNAME LOOP ===
        while (true) {
            System.out.print("Enter your username: ");
            // Sets username
            username = scanner.nextLine();
            user.setUsername(username);

            // Checks if username is correctly formatted
            if (user.checkUserName()) {
                System.out.println("Username successfully captured.");
                break;
            } else {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            }
        }

        // === PASSWORD LOOP ===
        while (true) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
            // Sets password
            user.setPassword(password);

            // Checks if password is correctly formatted
            if (user.checkPasswordComplexity()) {
                System.out.println("Password successfully captured.");
                break;
            } else {
                System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            }
        }

        // === PHONE NUMBER LOOP ===
        while (true) {
            System.out.print("Enter your phone number (with +27): ");
            phone = scanner.nextLine();
            // Sets phone number
            user.setCellPhone(phone);
            
            // Checks if phone number is correctly formatted
            if (user.checkCellPhoneNumber()) {
                System.out.println("Cell phone number successfully added.");
                break;
            } else {
                System.out.println("Cell phone number incorrectly formatted international code.");
            }
        }
        
        // Prints "User successfully registered."
        System.out.println(user.registerUser()); 

        // === LOGIN PROCESS ===
        System.out.println("\n=== Login ===");
        // === LOGIN LOOP ===
        while (true) {
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            // Checks if username and password match
            isMatch = user.loginUser(loginUsername, loginPassword);
            messageFeedback = user.returnLoginStatus(loginUsername, loginPassword);

            // Prints message feedback
            if (isMatch) {
                System.out.println(messageFeedback);
                break;
            } else {
                System.out.println(messageFeedback);
            }
        }

        scanner.close();
    }
}
