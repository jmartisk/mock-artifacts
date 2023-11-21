package org.acme;

import dev.langchain4j.agent.tool.Tool;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Tools {

    @Inject
    Mailer mailer;

    @Tool("Send an e-mail with the specified body, subject and destination address")
    public void sendEmail(String destination, String subject, String body) {
        mailer.send(Mail.withText(destination, subject, body));
    }

}
