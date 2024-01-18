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
                session.addMessageHandler(String.class, new MessageHandler.Whole<String>() {
                    @Override
                    public void onMessage(String s) {
                        receivedMessages.add(s);
                    }
                });
            }
        };
        try (Session session = ContainerProvider
                .getWebSocketContainer()
                .connectToServer(clientEndpoint, ClientEndpointConfig.Builder.create().build(), serverChatbotEndpoint)) {
            String firstResponse = receivedMessages.poll(30, TimeUnit.SECONDS);
            Assertions.assertNotNull(firstResponse); // this should be a new history with only the bot's introduction
            Assertions.assertTrue(firstResponse.contains("how can I help you?"), firstResponse);
            session.getBasicRemote().sendText("Who did the Japanese princess marry?");
            String secondResponse = receivedMessages.poll(30, TimeUnit.SECONDS);
            Assertions.assertNotNull(secondResponse);
            Assertions.assertTrue(secondResponse.contains("Kei Komuro"), secondResponse);
        }

    }
}
