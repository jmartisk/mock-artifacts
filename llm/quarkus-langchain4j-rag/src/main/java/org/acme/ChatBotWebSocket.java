package org.acme;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import io.quarkus.logging.Log;
import io.quarkus.qute.Template;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.WebSocket;

//@ServerEndpoint("/chatbot")
@WebSocket(path = "/chatbot")
public class ChatBotWebSocket {

    private final NewsService bot;

    public ChatBotWebSocket(NewsService bot) {
        this.bot = bot;
    }

    @Inject
    WebSocketConnection connection;

    @OnOpen
    public void onOpen() {
        Log.info("WebSocket sessionId open");
        bot.ask("Please introduce yourself");
        connection.sendText(updateChatMessages());
    }

//    @OnClose
//    void onClose() {
//        Log.info("WebSocket sessionId close");
//        ChatMemoryRemover.remove(bot, connection.id());
//    }

    @Inject
    ChatMemoryStore chatMemory;

    @Inject
    Template chatMessages;

    @OnTextMessage
    public String onMessage(String message) {
        Log.info("WebSocket received message: " + message);
        String chatMessage = parseChatMessage(message);
        String response = bot.ask(chatMessage);
        return updateChatMessages();
    }

    private String updateChatMessages() {
        String sessionId = connection.id();
        List<ChatMessage> messages = chatMemory.getMessages(sessionId);
        Collections.reverse(messages); // make sure new messages are at the top
        // ignore the first USER Hello message that was sent by the onOpen method
        messages = messages.stream().filter(m -> !m.text().startsWith("Please introduce yourself")).toList();
        // make the text of user messages more html-friendly (the RAG-added data contain newline characters)
        messages = messages.stream().map(m -> {
            if(m instanceof UserMessage) {
                String sanitizedText = Arrays.stream(m.text().split("\n")).collect(Collectors.joining("</p><p>", "<p>", "</p>"));
                return new UserMessage(sanitizedText);
            } else {
                return m;
            }
        }).collect(Collectors.toList());
        String html = chatMessages.data("messages", messages).render();
        return html;
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
            return message;
        }
    }

}
