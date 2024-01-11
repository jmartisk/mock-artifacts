package org.acme;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import io.quarkus.logging.Log;
import io.quarkus.qute.Template;
import io.vertx.core.parsetools.impl.JsonParserImpl;
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

    @Inject
    ChatMemoryStore chatMemory;

    @Inject
    Template chatMessages;

    @OnMessage
    public void onMessage(String message, Session session) {
        Log.info("WebSocket received message: " + message);
        String chatMessage = parseChatMessage(message);
        managedExecutor.execute(() -> {
            String response = bot.ask(session, chatMessage);
            try {
                List<ChatMessage> messages = chatMemory.getMessages(session);
                Collections.reverse(messages); // make sure new messages are at the top
                String html = chatMessages.data("messages", messages).render();
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
            JsonObject object = Json.createReader(new StringReader(message)).readObject();
            return object.getString("chat_message");
        } catch(Exception e) {
            e.printStackTrace();
            return message;
        }
    }

}
