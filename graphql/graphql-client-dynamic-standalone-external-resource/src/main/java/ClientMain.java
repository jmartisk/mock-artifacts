import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.vertx.dynamic.VertxDynamicGraphQLClientBuilder;
import io.vertx.core.Vertx;

import javax.json.JsonObject;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;


public class ClientMain {

    // Retrieve a list of countries. See https://github.com/trevorblades/countries
    public static void main(String[] args) throws Exception {
        Vertx vertx = Vertx.vertx();
        try (DynamicGraphQLClient client = new VertxDynamicGraphQLClientBuilder()
            .url("https://countries.trevorblades.com")
            .vertx(vertx)
            .build()) {
            Document document = document(
                operation(field("countries",
                    field("name"))));
            JsonObject data = client.executeSync(document).getData();
            System.out.println(data.toString());
        }
        finally {
            vertx.close();
        }
    }
}
