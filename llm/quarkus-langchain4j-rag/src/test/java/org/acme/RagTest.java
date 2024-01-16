package org.acme;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.websocket.ClientEndpointConfig;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@QuarkusTest
public class RagTest {

    @TestHTTPResource("chatbot")
    URI serverChatbotEndpoint;

    @Test
    public void test() throws DeploymentException, IOException, InterruptedException {
        LinkedBlockingDeque<String> receivedMessages = new LinkedBlockingDeque<>();
        Endpoint clientEndpoint = new Endpoint() {
            @Override
            public void onOpen(Session session, EndpointConfig config) {
                session.addMessageHandler((MessageHandler.Whole<String>) s -> receivedMessages.add(s));
            }
        };
        try (Session session = ContainerProvider
                .getWebSocketContainer()
                .connectToServer(clientEndpoint, ClientEndpointConfig.Builder.create().build(), serverChatbotEndpoint)) {
            String firstResponse = receivedMessages.poll(20, TimeUnit.SECONDS);
            Assertions.assertNotNull(firstResponse); // this should be a new history with only the bot's introduction
            Assertions.assertTrue(firstResponse.contains("How can I help you?"));
            session.getBasicRemote().sendText("How much does a CyberHouse cost? Answer with only the raw number in EUR, nothing else.");
            String secondResponse = receivedMessages.poll(20, TimeUnit.SECONDS);
            Assertions.assertNotNull(secondResponse);
            Assertions.assertTrue(secondResponse.contains("<td>775,000</td>"));
        }

    }
}
