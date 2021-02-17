
import io.smallrye.graphql.client.dynamic.RequestImpl;
import io.smallrye.graphql.client.dynamic.SmallRyeGraphQLDynamicClient;
import io.vertx.core.Vertx;
import org.eclipse.microprofile.graphql.client.core.Document;

import javax.json.JsonObject;
import java.util.concurrent.ExecutionException;

import static org.eclipse.microprofile.graphql.client.core.Document.document;
import static org.eclipse.microprofile.graphql.client.core.Field.field;
import static org.eclipse.microprofile.graphql.client.core.Operation.operation;

public class ClientMain {

    // Retrieve a list of countries. See https://github.com/trevorblades/countries
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (SmallRyeGraphQLDynamicClient client = new SmallRyeGraphQLDynamicClient.Builder()
                .url("https://countries.trevorblades.com")
                .build()) {
            Document document = document(
                    operation("CountriesQuery",
                            field("countries",
                                    field("name"))));
            RequestImpl request = new RequestImpl(document.build());
            JsonObject data = client.executeSync(request).getData();
            System.out.println(data.toString());
        }
        Vertx.vertx().close();
    }
}
