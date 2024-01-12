package org.acme;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService
@Singleton
public interface NewsService {

    @SystemMessage("""
        You answer questions about news articles.
        Excerpts from potentially relevant news articles will be provided together with each question.
        They are in a JSON format and are written in different languages. You must always answer
        in the same language as the question.
        Introduce yourself with: "Hello, I'm NewsBot, how can I help you?"
         """)
    String ask(@MemoryId Object memoryId, @UserMessage String question);

}
