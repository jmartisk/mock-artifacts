package org.acme;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.document.splitter.DocumentSplitters.recursive;

@ApplicationScoped
public class IngestData {

    @Inject
    EmbeddingStore<TextSegment> store;

    @Inject
    EmbeddingModel embeddingModel;

    @Inject
    @ConfigProperty(name = "data.file")
    File dataFile;

    @Startup
    public void init() {

        List<Document> documents = new ArrayList<>();
        try(JsonReader reader = Json.createReader(new FileReader(dataFile))) {
            JsonArray results = reader.readObject().getJsonArray("results");
            Log.info("Ingesting " + results.size() + " news reports...");
            for (JsonValue newsEntry : results) {
                JsonObject entryObject = newsEntry.asJsonObject();
                Document doc = new Document(entryObject.toString());
                documents.add(doc);
            }
            var ingestor = EmbeddingStoreIngestor.builder()
                    .embeddingStore(store)
                    .embeddingModel(embeddingModel)
                    .documentSplitter(recursive(1500, 0))
                    .build();
            ingestor.ingest(documents);
            Log.infof("Ingested %d news articles.", documents.size());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
