package org.acme.shop.client;

import io.quarkus.logging.Log;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.mutiny.subscription.Cancellable;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@Command(name = "dynamic", mixinStandardHelpOptions = true)
public class DynamicClientCommand implements Runnable {

    @CommandLine.Option(names = {"-o", "--operation"},
        description = "What to get. Values: customers, customersWithOrders, newOrders", required = true)
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
                case "newOrders":
                    subscribeToNewOrders();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void subscribeToNewOrders() {
        Document subOperation = document(
            operation(
                OperationType.SUBSCRIPTION,
                field("newOrders",
                    field("user",
                        field("name")),
                    field("items",
                        field("product",
                            field("name")),
                        field("quantity"))
                )
            )
        );
        Cancellable subscription = client.subscription(subOperation).subscribe().with(
            response -> Log.info(response.getData()),
            t -> t.printStackTrace()
        );
        Log.info("------ Listening for new orders now, press Enter to finish");
        try {
            System.in.read();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            subscription.cancel();
            Log.info("------ Finished listening for new orders");
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
