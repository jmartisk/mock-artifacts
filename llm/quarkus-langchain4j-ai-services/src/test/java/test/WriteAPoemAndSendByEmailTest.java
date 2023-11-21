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
public class WriteAPoemAndSendByEmailTest {

    @Inject
    AiResource ai;

    @Inject
    MockMailbox mailbox;

    @Test
    public void test() {
        ai.poem("a broken toy bulldozer", 6);
        List<Mail> list = mailbox.getMailsSentTo("test@test.com");
        assertEquals(1, list.size());
        Mail email = list.get(0);
        assertEquals("Poem", email.getSubject());
        assertFalse(email.getText().isEmpty());
        System.out.println("----- RESULTING POEM:");
        System.out.println(email.getText());
    }
}
