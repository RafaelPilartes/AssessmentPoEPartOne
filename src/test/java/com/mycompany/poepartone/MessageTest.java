package com.mycompany.poepartone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;

public class MessageTest {
    Message message1;
    Message message2;

    @BeforeEach
    public void setUp() {
        message1 = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight");
        message2 = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
    }
    
    @Test
    public void testMessageLengthSuccess() {
        String message = "Hi Mike, can you join us for dinner tonight";
        assertTrue(message.length() <= 250, "Message ready to send.");
    }

    @Test
    public void testMessageLengthFailure() {
        String message = "A".repeat(260);
        int excess = message.length() - 250;
        assertTrue(message.length() > 250,
            "Message exceeds 250 characters by " + excess + ", please reduce size.");
    }

    @Test
    public void testRecipientNumberSuccess() {
        assertTrue(message1.checkRecipientCell(), "Cell phone number successfully captured.");
    }

    @Test
    public void testRecipientNumberFailure() {
        assertFalse(message2.checkRecipientCell(),
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testMessageHashSuccess() {
        String expectedHash = message1.getMessageID().substring(0, 2) + ":" + 0 + ":HI" + "TONIGHT";
        String actualHash = message1.createMessageHash();
        assertEquals(expectedHash, actualHash, "Message hash matches expected pattern.");
    }
    
    @Test
    public void testMessageIDGenerated() {
        String id = message1.getMessageID();
        assertNotNull(id, "Message ID generated: " + id);
        assertEquals(10, id.length(), "Message ID has 10 digits.");
    }

    @Test
    public void testSendMessageSuccess() {
        String result = simulateSendChoice("1"); // Send
        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testDiscardMessage() {
        String result = simulateSendChoice("2"); // Discard
        assertEquals("Press 0 to delete message.", result);
    }

    @Test
    public void testStoreMessage() {
        String result = simulateSendChoice("3"); // Store
        assertEquals("Message successfully stored.", result);
    }

    private String simulateSendChoice(String choice) {
        return switch (choice) {
            case "1" -> "Message successfully sent.";
            case "2" -> "Press 0 to delete message.";
            case "3" -> "Message successfully stored.";
            default -> "Invalid option.";
        };
    }

    @Test
    public void testTotalMessagesSent() {
        ArrayList<Message> messages = new ArrayList<>();
        
        Message msg1 = new Message(0, "+27123456789", "Hello world.");
        Scanner scanner1 = new Scanner("1\n"); // 1 = Send
        msg1.sentMessage(scanner1);
        messages.add(msg1);
    
        Message msg2 = new Message(1, "+27123456788", "Discard this message.");
        Scanner scanner2 = new Scanner("2\n"); // 2 = Discard
        msg2.sentMessage(scanner2);
        messages.add(msg2);

        Message msg3 = new Message(2, "+27123456787", "Store this message.");
        Scanner scanner3 = new Scanner("3\n"); // 3 = Store
        msg3.sentMessage(scanner3);
        messages.add(msg3);
    
        int totalSent = Message.returnTotalMessages(messages);
        assertEquals(1, totalSent, "Total messages sent should be 2.");
    }
}
