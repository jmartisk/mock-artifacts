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

import jakarta.inject.Inject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@Command(name = "dynamic", mixinStandardHelpOptions = true)
public class DynamicClientCommand implements Runnable {

    @CommandLine.Option(names = {"-o", "--operation"},
        description = "What to get. Values: users, randomUsers", required = true)
    String target;

    @Inject
    @GraphQLClient("shopDynamic")
    DynamicGraphQLClient client;

    @Override
    public void run() {
        try {
            switch (target) {
                case "users":
                    getUsers();
                    break;
                case "randomUsers":
                    getRandomUsers();
                    break;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getRandomUsers() {
        Document subOperation = document(
            operation(
                OperationType.SUBSCRIPTION,
                field("randomUsers",
                    field("username"),
                    field("email")
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

    private void getUsers() throws ExecutionException, InterruptedException {
        Document query = document(
            operation(
                field("randomUsers",
                    field("username"),
                    field("email")
                )
            )
        );
        Response response = client.executeSync(query);
        Log.info(response.getData());
    }


}
