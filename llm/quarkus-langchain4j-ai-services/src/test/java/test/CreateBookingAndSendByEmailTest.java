package test;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.MockMailbox;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.AiResource;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
public class CreateBookingAndSendByEmailTest {

    @Inject
    AiResource ai;

    @Inject
    MockMailbox mailbox;

    @Test
    public void test() {
        ai.create("John Doe", "john@gmail.com");
        List<Mail> list = mailbox.getMailsSentTo("john@gmail.com");
        assertEquals(1, list.size());
        Mail email = list.get(0);
        assertEquals("Booking confirmation", email.getSubject());
        assertFalse(email.getText().isEmpty());
        System.out.println("----- RESULTING MAIL:");
        System.out.println(email.getText());
    }
}
