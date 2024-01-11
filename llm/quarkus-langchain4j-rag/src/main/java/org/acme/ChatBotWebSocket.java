package org.acme;

import java.io.IOException;
import java.io.StringReader;

import com.fasterxml.jackson.core.JsonParser;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import org.eclipse.microprofile.context.ManagedExecutor;

import io.quarkiverse.langchain4j.ChatMemoryRemover;

@ServerEndpoint("/chatbot")
public class ChatBotWebSocket {

    @Inject
    CharlieKnower bot;

    @Inject
    ManagedExecutor managedExecutor;

    @OnOpen
    public void onOpen(Session session) {
        Log.info("WebSocket session open");
        managedExecutor.execute(() -> {
            String response = bot.ask(session, "Hello");
            try {
                session.getBasicRemote().sendText(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @OnClose
    void onClose(Session session) {
        Log.info("WebSocket session close");
        ChatMemoryRemover.remove(bot, session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        Log.info("WebSocket message: " + message);
        String chatMessage = parseChatMessage(message);
        managedExecutor.execute(() -> {
            String response = bot.ask(session, chatMessage);
            try {
                String html = """
                            <div id="chatContent">
                            %s
                            </div>
                        """.formatted(response);
                session.getBasicRemote().sendText(html);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    /**
     * If the message is in JSON format (it was sent by HTMX, parse it and return just the "chat_message" field.
     * If it is plain text, return the message as is.
     */
    private String parseChatMessage(String message) {
        try {
            JsonObject object = Json.createParser(new StringReader(message)).getObject();
            return object.getString("chat_message");
        } catch(Exception e) {
            return message;
        }
    }

}
