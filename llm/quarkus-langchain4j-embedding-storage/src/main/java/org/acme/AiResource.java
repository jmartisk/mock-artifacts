package org.acme;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/")
public class AiResource {

    @Inject
    EmbeddingStore<TextSegment> store;

    private final EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/embeddings")
    public void embeddings() {
        embed("Charlie is wearing a hat", Metadata.from("k1", "v1"));
        embed("Charlie has a big tummy");
        embed("Charlie broke his bulldozer");
        embed("Charlie has a lot of toys", Metadata.from("k3", "v3"));
        embed("Charlie likes construction vehicles");
        embed("One year ago, Charlie was three years old");
        embed("David can move stuff around with his mind");
        embed("Pedro can count");
        embed("Charlie likes candy");
        embed("I believe in aliens");

        Response<Embedding> question = embeddingModel.embed("Does Charlie have a bulldozer?");
        List<EmbeddingMatch<TextSegment>> relevantEmbeddings = store.findRelevant(question.content(), 5);

        for (EmbeddingMatch<TextSegment> match : relevantEmbeddings) {
            String text = match.embedded() != null ? match.embedded().text() : null;
            Metadata metadata = match.embedded() != null ? match.embedded().metadata() : null;
            System.out.println();
            System.out.println("Relevant embedding ID = " + match.embeddingId() + "\n" +
                    "text = " + text + "\n" +
                    "metadata = " + metadata + "\n" +
                    "score = " + match.score());
        }
    }

    private void embed(String string) {
        embed(string, new Metadata());
    }

    private void embed(String string, Metadata metadata) {
        TextSegment textSegment = new TextSegment(string, metadata);
        Embedding embedding = embeddingModel.embed(textSegment).content();
        store.add(embedding, textSegment);
    }

}
