import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.vertx.core.Vertx;

import javax.json.JsonObject;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;


public class ClientMain {

    // Retrieve a list of countries. See https://github.com/trevorblades/countries
    public static void main(String[] args) {
        try (DynamicGraphQLClient client = DynamicGraphQLClientBuilder.newBuilder()
              .url("https://countries.trevorblades.com")
              .build()) {
            Document document = document(
                operation(field("countries",
                    field("name"))));
            JsonObject data = client.executeSync(document).getData();
            System.out.println(data.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            Vertx.vertx().close();
        }
    }
}
