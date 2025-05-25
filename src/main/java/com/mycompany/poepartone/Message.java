package com.mycompany.poepartone;

import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String content;
    private String messageHash;
    private String status;

    public Message(int messageNumber, String recipient, String content) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.content = content;
        this.messageHash = createMessageHash();
    }

    // Custom Message ID generator (Created by me, idea assisted by stackoverflow)
    private String generateMessageID() {
        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "");

        while (uuid.length() < 10) {
            uuid += (int)(Math.random() * 10); // Add random digit
        }

        return uuid.substring(0, 10);
    }

    public boolean checkMessageID() {
        return messageID.length() == 10;
    }

    public boolean checkRecipientCell() {
        return recipient.matches("\\+27\\d{9}");
    }

    public String createMessageHash() {
        String[] words = content.trim().split("\\s+"); // Logic to split by stackoverflow: https://stackoverflow.com/questions/40090776/what-does-s-split-s-means-here-in-the-below-code
        String first = words[0].toUpperCase();
        String last = words[words.length - 1].toUpperCase();
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last;
    }

    public String sentMessage(Scanner scanner) {
        System.out.println("Choose an option:\n1) Send Message\n2) Disregard Message\n3) Store Message");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            this.status = "Sent";
            return "Message successfully sent.";
        } else if (choice == 2) {
            this.status = "Discarded";
            return "Press 0 to delete message.";
        } else if (choice == 3) {
            this.status = "Stored";
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid option selected.";
        }
    }

    // Storing as JSON (used ChatGPT to get correct org.json.simple syntax)
    public void storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageID", messageID);
        json.put("messageNumber", messageNumber);
        json.put("recipient", recipient);
        json.put("message", content);
        json.put("messageHash", messageHash);

        JSONArray container = new JSONArray();
        container.add(json);

        try (FileWriter file = new FileWriter("storedMessages.json", true)) {
            file.write(container.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public static void printMessageDetails(Message msg) {
        JOptionPane.showMessageDialog(null,
            "Message Hash: " + msg.messageHash +
            "\nRecipient: " + msg.recipient +
            "\nMessage: " + msg.content +
            "\nStatus: " + msg.status
        );
    }

    // Total counter logic: fully mine
    public static int returnTotalMessages(ArrayList<Message> messages) {
        int total = 0;
        for (Message msg : messages) {
            if (msg.status != null && "Sent".equals(msg.status)) {
                total++;
            }
        }
        return total;
    }
    
    
    // Getters for message status
    public String getMessageStatus() {
        return this.status;
    }

    public String getMessageID() {
        return this.messageID;
    }
}

