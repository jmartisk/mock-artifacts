package org.acme.shop.client;

import io.quarkus.logging.Log;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

import java.util.concurrent.ExecutionException;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@Command(name = "get", mixinStandardHelpOptions = true)
public class GetCommand implements Runnable {

    @CommandLine.Option(names = {"-t", "--target"},
        description = "What to get. Values: customers, customersWithOrders", required = true)
    String target;

    @Inject
    @GraphQLClient("shopDynamic")
    DynamicGraphQLClient client;

    @Override
    public void run() {
        try {
            switch (target) {
                case "customers":
                    getCustomers();
                    break;
                case "customersWithOrders":
                    getCustomersWithOrders();
                    break;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomers() throws ExecutionException, InterruptedException {
        Document query = document(
            operation(
                field("customers",
                    field("name"),
                    field("id")
                )
            )
        );
        Response response = client.executeSync(query);
        Log.info(response.getData());
    }

    private void getCustomersWithOrders() throws ExecutionException, InterruptedException {
        Document query = document(
            operation(
                field("customers",
                    field("name"),
                    field("orders",
                        field("items",
                            field("product",
                                field("name")
                            ),
                            field("quantity")
                        )
                    )
                )
            )
        );
        Response response = client.executeSync(query);
        Log.info(response.getData());
    }

}
