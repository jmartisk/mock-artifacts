package org.acme;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import io.quarkus.arc.ClientProxy;
import io.quarkus.logging.Log;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Inject
    @ConfigProperty(name = "max.entries", defaultValue = "99999")
    Integer maxEntries;

    @Startup
    public void init() throws InterruptedException {
        List<Document> documents = new ArrayList<>();
        try(JsonReader reader = Json.createReader(new FileReader(dataFile))) {
            JsonArray results = reader.readArray();
            Log.info("Ingesting news reports...");
            int i = 0;
            for (JsonValue newsEntry : results) {
                i++;
                if(i > maxEntries) {
                    break;
                }
                String content = newsEntry.asJsonObject().getString("content", null);
                if(content != null && !content.isEmpty()) {
                    Document doc = new Document(content);
                    documents.add(doc);
                    continue;
                }
                String fullDescription = newsEntry.asJsonObject().getString("full_description", null);
                if(fullDescription != null && !fullDescription.isEmpty()) {
                    Document doc = new Document(fullDescription);
                    documents.add(doc);
                    continue;
                }
                String description = newsEntry.asJsonObject().getString("description", null);
                if(description != null && !description.isEmpty()) {
                    Document doc = new Document(description);
                    documents.add(doc);
                    continue;
                }
            }
            var ingestor = EmbeddingStoreIngestor.builder()
                    .embeddingStore(store)
                    .embeddingModel(embeddingModel)
                    .documentSplitter(recursive(1000, 50))
                    .build();

            if(ClientProxy.unwrap(embeddingModel).getClass().getName().contains("mistral")) {
                // ingest in batches of 25 documents
                // this is to avoid getting a 'message too long' error from mistral
                // .. and limit requests to 5/sec
                int batchSize = 25;
                for (int x = 0; x < documents.size(); x += batchSize) {
                    int end = Math.min(x + batchSize, documents.size());
                    List<Document> batch = documents.subList(x, end);
                    ingestor.ingest(batch);
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            } else {
                ingestor.ingest(documents);
            }
            Log.infof("Ingested %d news articles.", documents.size());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
