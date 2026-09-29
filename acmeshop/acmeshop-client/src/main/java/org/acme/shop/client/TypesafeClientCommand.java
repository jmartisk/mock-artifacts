package org.acme.shop.client;

import io.quarkus.logging.Log;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.mutiny.subscription.Cancellable;
import jakarta.inject.Inject;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.IOException;

@Command(name = "typesafe", mixinStandardHelpOptions = true)
public class TypesafeClientCommand implements Runnable {

    @CommandLine.Option(names = {"-o", "--operation"},
            description = "What to get. Values: users, randomUsers", required = true)
    String target;

    @Inject
    @GraphQLClient("shopTypesafe")
    ShopClient client;

    @Override
    public void run() {
        try {
            switch (target) {
                case "users":
                    getUsers();
                    break;
                case "randomUsers":
                    getRandomUsers();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getRandomUsers() {
        Cancellable subscription = client.randomUsers().subscribe().with(
                user -> Log.info(user),
                Throwable::printStackTrace
        );
        Log.info("------ Listening for new random users now, press Enter to finish");
        try {
            System.in.read();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            subscription.cancel();
            Log.info("------ Finished listening");
        }

    }

    private void getUsers() {
        System.out.println(client.getUsers());
    }


}
