package org.example.graphql.client;


import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Argument;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.InputObject;
import io.smallrye.graphql.client.core.InputObjectField;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.core.Variable;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static io.smallrye.graphql.client.core.Argument.arg;
import static io.smallrye.graphql.client.core.Argument.args;
import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.InputObject.inputObject;
import static io.smallrye.graphql.client.core.InputObjectField.prop;
import static io.smallrye.graphql.client.core.Operation.operation;

public class ClientMainMutation {

    public static void main(String[] args) throws Exception {
        DynamicGraphQLClient client = DynamicGraphQLClientBuilder.newBuilder()
                .url("http://localhost:8080/graphql")
                .build();
        try {
            String newUserName = "user" + Math.abs(ThreadLocalRandom.current().nextInt());
            Document query = document(
                    operation(
                            OperationType.MUTATION,
                            // name of the mutation
                            field("create",
                                    // input argument
                                    args(arg("person", inputObject(prop("name", newUserName)))),
                                    // selected fields to be returned back
                                    field("name")
                            )
                    ));
            System.out.println("QUERY: " + query.build());
            Response response = client.executeSync(query);
            System.out.println("RESPONSE: " + response.getData().toString());
        }
        finally {
            client.close();
            System.exit(0);
        }
    }
}
