package org.acme;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class IngestData {

    @Inject
    EmbeddingStore<TextSegment> store;

    @Inject
    EmbeddingModel embeddingModel;

    @Startup
    public void init() {
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
        Log.info("Ingested embeddings...");
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
