package org.acme;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService(
        chatMemoryProviderSupplier = MySmallMemoryProvider.class,
        tools = Tools.class
)
public interface PoetService {

    @SystemMessage("You are a professional poet.")
    @UserMessage("Write a poem about {topic}. " +
            "The poem should be {lines} lines long.")
    String writeAPoem(String topic, int lines);

    @UserMessage("Send the previous message as email with subject \"{subject}\" " +
            "to {toAddress}.")
    String sendEmail(String subject, String toAddress);

}
