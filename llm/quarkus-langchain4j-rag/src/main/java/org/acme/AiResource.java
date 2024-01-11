package org.acme;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.quarkus.logging.Log;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.runtime.Startup;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Path("/")
public class AiResource {

    @Inject
    EmbeddingStore<TextSegment> store;

    @Inject
    EmbeddingModel embeddingModel;

    @Inject
    CharlieKnower charlieKnower;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/embeddings")
    public String search_for_relevant_embeddings(@RestQuery String question) {
        Log.info("Searching for relevant embeddings for question: " + question);
        Response<Embedding> questionEmbedding = embeddingModel.embed(question);
        List<EmbeddingMatch<TextSegment>> relevantEmbeddings = store.findRelevant(questionEmbedding.content(), 5);

        Log.info("Found relevant embeddings (count: " + relevantEmbeddings.size() + "):");
        for (EmbeddingMatch<TextSegment> match : relevantEmbeddings) {
            String text = match.embedded() != null ? match.embedded().text() : null;
            Metadata metadata = match.embedded() != null ? match.embedded().metadata() : null;
            System.out.println();
            System.out.println("Relevant embedding ID = " + match.embeddingId() + "\n" +
                    "text = " + text + "\n" +
                    "metadata = " + metadata + "\n" +
                    "score = " + match.score());
        }
        return "OK, look at the server log\n";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/question")
    public String askQuestion(@RestQuery String question) {
        String answer = charlieKnower.ask(ThreadLocalRandom.current().nextLong(), question);
        Log.info("ANSWER: " + answer);
        return answer;
    }

    @Inject
    Template index;

    @Produces(MediaType.TEXT_HTML)
    @GET
    @Path("/")
    public TemplateInstance index() {
        return index.instance();
    }

}
