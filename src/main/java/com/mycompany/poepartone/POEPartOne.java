package com.mycompany.poepartone;
import java.util.Scanner;
import java.util.ArrayList;

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
        
        // === SEND MESSAGES ===
        if (isMatch) {
            System.out.println("\nWelcome to QuickChat!");

            ArrayList<Message> sentMessages = new ArrayList<>();

            boolean active = true;
            while (active) {
                System.out.println("\nSelect an option:\n1) Send Messages\n2) Show recently sent messages\n3) Quit");
                int option = scanner.nextInt();

                if (option == 1) {
                    System.out.println("How many messages do you want to send?");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    for (int i = 1; i <= quantity; i++) {
                        System.out.println("\nEnter recipient phone number (+...):");
                        String recipient = scanner.nextLine();

                        System.out.println("Enter message (max 250 chars):");
                        String content = scanner.nextLine();

                        if (content.length() > 250) {
                            System.out.println("Message exceeds 250 characters by " + (content.length() - 250) + ", please reduce size.");
                            i--;
                            continue;
                        }

                        Message message = new Message(i, recipient, content);

                        if (!message.checkRecipientCell()) {
                            System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                            i--;
                            continue;
                        }

                        System.out.println(message.sentMessage(scanner));
                        Message.printMessageDetails(message);

                        String status = message.getMessageStatus();
                        if (status.equals("Sent")) {
                            sentMessages.add(message);
                        }
                    }

                    int totalSent = Message.returnTotalMessages(sentMessages);
                    System.out.println("\nTotal messages sent: " + totalSent);
                } 
                else if (option == 2) {
                    System.out.println("Coming Soon.");
                } 
                else if (option == 3) {
                    System.out.println("Goodbye!");
                    active = false;
                }
                else {
                    System.out.println("Invalid option.");
                }
            }
        }

        scanner.close();
    }
}
