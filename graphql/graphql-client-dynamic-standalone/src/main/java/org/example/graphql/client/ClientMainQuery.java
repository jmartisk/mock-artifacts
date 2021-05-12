package org.example.graphql.client;


import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.vertx.core.Vertx;

import java.util.concurrent.ExecutionException;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

public class ClientMainQuery {

    public static void main(String[] args) throws Exception {
        DynamicGraphQLClient client = DynamicGraphQLClientBuilder.newBuilder()
                .url("http://localhost:8080/graphql")
                .build();
        try {
            Document query = document(
                    operation(
                            field("all", field("name"))
                    )
            );
            Response response = client.executeSync(query);
            System.out.println(response.getData().toString());
        } finally {
            client.close();
            System.exit(0);
        }
    }
}
