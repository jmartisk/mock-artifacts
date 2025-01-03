package org.example.graphql.client;


import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.smallrye.mutiny.Multi;

import java.util.concurrent.CountDownLatch;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

public class ClientMainSubscription {

    public static void main(String[] args) throws Exception {
        DynamicGraphQLClient client = DynamicGraphQLClientBuilder.newBuilder()
                .url("http://localhost:8080/graphql")
//                .header("Authorization", "Bearer XXX")
                .build();
        try {
            Document query = document(
                    operation(
                            OperationType.SUBSCRIPTION,
                            field("newPeople",
                                    field("name")
                            )
                    ));
            final CountDownLatch FINISHED = new CountDownLatch(1);
            System.out.println("QUERY: " + query.build());
            Multi<Response> events = client.subscription(query);
            events.subscribe().with(
                    // event handler
                    response -> {
                        System.out.println("RECEIVED EVENT: " + response);
                    },
                    // failure handler
                    t -> {
                        System.out.println("FAILED!");
                        t.printStackTrace();
                    },
                    // close handler
                    () -> {
                        System.out.println("COMPLETE!");
                        FINISHED.countDown();
                    });

            FINISHED.await();
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
