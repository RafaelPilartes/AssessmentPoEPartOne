package com.mycompany.poepartone;

import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.UUID;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String content;
    private String messageHash;
    private String status;

    // Arrays to store messages and hashes
    public static ArrayList<Message> sentMessages = new ArrayList<>();
    public static ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static ArrayList<Message> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();
    
    public Message(int messageNumber, String recipient, String content) {
        this.messageID = generateMessageID();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.content = content;
        this.messageHash = createMessageHash();
        this.status = null;
        messageIDs.add(this.messageID);
        messageHashes.add(this.messageHash);
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
        String first = words.length > 0 ? words[0].toUpperCase() : "";
        String last = words.length > 0 ? words[words.length - 1].toUpperCase() : "";
        return messageID.substring(0, 2) + ":" + messageNumber + ":" + first + last;
    }

    public String sentMessage(Scanner scanner) {
        System.out.println("Choose an option:\n1) Send Message\n2) Disregard Message\n3) Store Message");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            this.status = "Sent";
            sentMessages.add(this);
            return "Message successfully sent.";
        } else if (choice == 2) {
            this.status = "Discarded";
            disregardedMessages.add(this);
            return "Press 0 to delete message.";
        } else if (choice == 3) {
            this.status = "Stored";
            storedMessages.add(this);
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
        json.put("status", status);

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
    
    
    // Getters
    public String getMessageStatus() {
        return this.status;
    }

    public String getMessageID() {
        return this.messageID;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getContent() {
        return this.content;
    }

    public String getMessageHash() {
        return this.messageHash;
    }
    
    // Setters
    public void setStatus(String status) {
        this.status = status;
    }
    
    // --------------- NEW FEATURES -------------------
    public static void displaySendersAndRecipients(String sender) {
        System.out.println("=== Senders and recipients of all sent messages ===");
        if (Message.sentMessages.isEmpty()) {
            System.out.println("No sent messages found.");
        } else {
            System.out.println("Senders and recipients of all sent messages:");
            for (Message msg : sentMessages) {
                System.out.println("Sender: " +  sender + " | Recipient: " + msg.recipient);
            }
        }
    }
    
    public static Message getLongestSentMessage() {
        System.out.println("=== Longest sent messagages ===");
        return sentMessages.stream()
                .max(Comparator.comparingInt(m -> m.content.length()))
                .orElse(null);
    }
    
    public static void searchMessageByID(String id) {
        System.out.println("=== Message by ID ===");
        if (Message.sentMessages.isEmpty()) {
            System.out.println("No sent messages.");
        } else {
            boolean found = false;
            
            for (Message msg : sentMessages) {
                if (msg.messageID.equals(id)) {
                    System.out.println("Recipient: " + msg.recipient);
                    System.out.println("Message: " + msg.content);
                    return;
                }
            }
            if (!found) {
                System.out.println("Message ID not found.");
            }
        }
    }
    
    public static void searchMessagesByRecipient(String recipient) {
        System.out.println("=== Messages by Recipient ===");
        if (Message.sentMessages.isEmpty()) {
            System.out.println("No sent messages.");
        } else {
            boolean found = false;
            for (Message msg : sentMessages) {
                if (msg.recipient.equals(recipient)) {
                    System.out.println("Message to " + recipient + ": " + msg.content);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No messages found for recipient " + recipient);
            }
        }
    }
    
    public static boolean deleteMessageByHash(String hash) {
        Iterator<Message> iterator = sentMessages.iterator();
        while (iterator.hasNext()) {
            Message msg = iterator.next();
            if (msg.messageHash.equals(hash)) {
                iterator.remove();
                messageHashes.remove(hash);
                messageIDs.remove(msg.messageID);
                System.out.println("Message with hash " + hash + " deleted.");
                return true;
            }
        }
        System.out.println("No message with hash " + hash + " found.");
        return false;
    }
    
    public static void displaySentMessageReport() {
        System.out.println("=== Sent Messages Report ===");
        if (Message.sentMessages.isEmpty()) {
            System.out.println("No sent messages.");
        } else {
            for (Message msg : sentMessages) {
                System.out.println("ID: " + msg.messageID);
                System.out.println("Recipient: " + msg.recipient);
                System.out.println("Message: " + msg.content);
                System.out.println("Hash: " + msg.messageHash);
                System.out.println("Status: " + msg.status);
                System.out.println("--------------------------");
            }
        }
    }
}

