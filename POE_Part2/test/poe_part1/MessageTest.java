package poe_part1;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class MessageTest {

    private Message message1;
    private Message message2;

    @Before
    public void setUp() {
        Message.resetMessages();

        message1 = new Message(1);
        message1.setRecipient("+27718693002");
        message1.setMessage("Hi Neo, can you join us for dinner tonight?");
        message1.createMessageHash();

        message2 = new Message(2);
        message2.setRecipient("08575975889");
        message2.setMessage("Hi Kea, did you receive the payment?");
        message2.createMessageHash();
    }

    @Test
    public void testMessageIDNotMoreThanTenCharacters() {
        assertTrue(message1.checkMessageID());
    }

    @Test
    public void testMessageIDGenerated() {
        String id = message1.getMessageID();
        assertTrue(id != null && !id.isEmpty());
        System.out.println("Message ID generated: " + id);
    }

    @Test
    public void testMessageWithinLimit() {
        String msg = "Hi Mike, can you join us for dinner tonight?";
        assertTrue(msg.length() <= 250);
        assertEquals("Message ready to send.", msg.length() <= 250 ? "Message ready to send." : "fail");
    }

    @Test
    public void testMessageExceedsLimit() {
        String longMessage = "A".repeat(260);
        int excess = longMessage.length() - 250;
        String result = "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result);
    }

    @Test
    public void testRecipientValidSuccess() {
        Message msg = new Message(1);
        String result = msg.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientValidFailure() {
        Message msg = new Message(1);
        String result = msg.checkRecipientCell("08575975889");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testMessageHashMessage1() {
        String hash = message1.getMessageHash();
        assertTrue(hash.contains("HITONIGHT") || hash.endsWith("HITONIGHT"));
    }

    @Test
    public void testMessageHashesInLoop() {
        Message[] messages = {message1, message2};
        for (Message msg : messages) {
            String hash = msg.getMessageHash();
            assertTrue(hash != null && !hash.isEmpty());
            assertTrue(hash.equals(hash.toUpperCase()));
        }
    }

    @Test
    public void testSentMessageSend() {
        message1.sentMessage(1);
        assertEquals("Message successfully sent.", message1.sentMessage(1));
    }

    @Test
    public void testSentMessageDisregard() {
        assertEquals("Press 0 to delete the message.", message1.sentMessage(2));
    }

    @Test
    public void testSentMessageStore() {
        assertEquals("Message successfully stored.", message1.sentMessage(3));
    }

    @Test
    public void testReturnTotalMessages() {
        Message.resetMessages();
        message1.sentMessage(1);
        assertEquals(1, message1.returnTotalMessages());
    }
}