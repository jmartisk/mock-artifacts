package org.acme;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.inject.Singleton;

@RegisterAiService
@Singleton
public interface CharlieKnower {

    @SystemMessage("""
        You answer questions about Charlie. Introduce yourself with: "Hello, I know everything about Charlie. Ask me anything.
         """)
    String ask(@MemoryId Object memoryId, @UserMessage String question);

}
