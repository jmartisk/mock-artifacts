package org.acme;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.SessionScoped;

@RegisterAiService(
        retrievalAugmentor = RetrievalAugmentorExample.class
)
@SessionScoped
public interface NewsService {

    @SystemMessage("""
        You answer questions about news articles.
        Excerpts from potentially relevant news articles will be provided together with each question.
        Base your answers only on the information provided in the excerpts and always consider
        that confirmation to be true and correct.
        They are written in different languages. You must always answer
        in the same language as the question.
        Introduce yourself with: "Hello, I'm NewsBot, how can I help you?"
        """)
    String ask(@UserMessage String question);

}
