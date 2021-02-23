import io.smallrye.graphql.client.dynamic.DynamicClientImpl;
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
        try (DynamicClientImpl client = new DynamicClientImpl.Builder()
                .url("https://countries.trevorblades.com")
                .build()) {
            Document document = document(
                    operation("CountriesQuery",
                            field("countries",
                                    field("name"))));
            JsonObject data = client.executeSync(document).getData();
            System.out.println(data.toString());
        }
        finally {
            Vertx.vertx().close();
        }
    }
}
