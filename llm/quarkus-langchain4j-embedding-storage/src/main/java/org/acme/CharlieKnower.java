package org.acme;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface CharlieKnower {

    @SystemMessage("You answer questions about Charlie.")
    String ask(String question);

}
