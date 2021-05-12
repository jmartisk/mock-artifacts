package org.example.graphql;

import io.smallrye.graphql.client.NamedClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.ExecutionException;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@Path("/")
public class ActionResource {

    @Inject
    @NamedClient("people")
    DynamicGraphQLClient client;

    @Path("/injected")
    @GET
    public String callWithClient_injected() throws ExecutionException, InterruptedException {
        Document query = document(
                operation(
                        field("all", field("name"))
                )
        );
        Response response = client.executeSync(query);
        return response.getData().toString();
    }

    @Path("/manual")
    @GET
    public String callWithClient_manual() throws ExecutionException, InterruptedException {
        DynamicGraphQLClient client = DynamicGraphQLClientBuilder.newBuilder()
                .url("http://localhost:8080/graphql")
                .build();
        Document query = document(
                operation(
                        field("all", field("name"))
                )
        );
        Response response = client.executeSync(query);
        return response.getData().toString();
    }

}
