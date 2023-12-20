package org.acme;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RetrieverExample implements Retriever<TextSegment> {

    private final EmbeddingStoreRetriever retriever;

    RetrieverExample(EmbeddingStore<TextSegment> store, EmbeddingModel model) {
        retriever = EmbeddingStoreRetriever.from(store, model, 3);
    }

    @Override
    public List<TextSegment> findRelevant(String s) {
        List<TextSegment> relevant = retriever.findRelevant(s);
        Log.info("Relevant embeddings found by the retriever: " + relevant);
        return relevant;
    }
}
