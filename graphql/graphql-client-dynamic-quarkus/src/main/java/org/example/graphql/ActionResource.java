package org.example.graphql;

import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.smallrye.mutiny.Multi;
import org.jboss.resteasy.reactive.RestSseElementType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@Path("/")
public class ActionResource {

    @Inject
    @GraphQLClient("people")
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

    /**
     * This requests a subscription over a websocket, and basically translates that websocket
     * to server sent events.
     */
    @Path("/subscription")
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.TEXT_PLAIN)
    public Multi<String> subscription() {
        Document query = document(
                operation(
                        OperationType.SUBSCRIPTION,
                        field("newPeopleShared", field("name"))
                )
        );
        return client.subscription(query)
                .map(Response::toString);
    }

}
