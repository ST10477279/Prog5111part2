package poe_part1;

import java.util.ArrayList;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Message {

    private static ArrayList<String[]> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    private String messageID;
    private String messageHash;
    private String recipient;
    private String message;
    private int messageNumber;

    public Message(int messageNumber) {
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
    }

    private String generateMessageID() {
        Random random = new Random();
        long id = (long) (random.nextDouble() * 9000000000L) + 1000000000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell(String number) {
        if (number == null || number.isEmpty()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        if (number.length() > 10 && !number.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        if (!number.startsWith("+") && number.length() > 10) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        this.recipient = number;
        return "Cell phone number successfully captured.";
    }

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        messageHash = (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return messageHash;
    }

    public String sentMessage(int choice) {
        if (choice == 1) {
            totalMessagesSent++;
            String[] details = {messageID, messageHash, recipient, message};
            sentMessages.add(details);
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        }
        return "Invalid option.";
    }

    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent.";
        }
        StringBuilder sb = new StringBuilder();
        for (String[] msg : sentMessages) {
            sb.append("Message ID: ").append(msg[0]).append("\n");
            sb.append("Message Hash: ").append(msg[1]).append("\n");
            sb.append("Recipient: ").append(msg[2]).append("\n");
            sb.append("Message: ").append(msg[3]).append("\n");
            sb.append("----------------------------\n");
        }
        return sb.toString();
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageID", messageID);
        jsonObject.put("messageHash", messageHash);
        jsonObject.put("recipient", recipient);
        jsonObject.put("message", message);
        jsonArray.add(jsonObject);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(jsonArray.toJSONString());
            file.write("\n");
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public static void resetMessages() {
        sentMessages.clear();
        totalMessagesSent = 0;
    }
}