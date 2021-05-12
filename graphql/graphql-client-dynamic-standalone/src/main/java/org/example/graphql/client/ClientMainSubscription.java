package org.example.graphql.client;


import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.smallrye.mutiny.Multi;

import java.util.concurrent.TimeUnit;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

public class ClientMainSubscription {

    public static void main(String[] args) throws Exception {
        DynamicGraphQLClient client = DynamicGraphQLClientBuilder.newBuilder()
                .url("http://localhost:8080/graphql")
                .build();
        try {
            Document query = document(
                    operation(
                            OperationType.SUBSCRIPTION,
                            field("multi",
                                    field("name")
                            )
                    ));
            System.out.println("QUERY: " + query.build());
            Multi<Response> subscribe = client.subscribe(query); // TODO: should the client lib perform the subscribe?
            subscribe.subscribe().with(response -> {
                System.out.println("RESPONSE: " + response.getData());
                System.out.println(response.getErrors());
            });
            TimeUnit.SECONDS.sleep(30);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            client.close();
            System.exit(0);
        }
    }
}
