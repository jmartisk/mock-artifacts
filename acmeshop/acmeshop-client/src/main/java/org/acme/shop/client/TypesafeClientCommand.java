package org.acme.shop.client;

import io.quarkus.logging.Log;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.mutiny.subscription.Cancellable;
import org.acme.shop.client.model.Customer;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import jakarta.inject.Inject;
import java.io.IOException;
import java.util.List;

@Command(name = "typesafe", mixinStandardHelpOptions = true)
public class TypesafeClientCommand implements Runnable {

    @CommandLine.Option(names = {"-o", "--operation"},
        description = "What to get. Values: customers, newOrders", required = true)
    String target;

    @Inject
    @GraphQLClient("shopTypesafe")
    ShopClient client;

    @Override
    public void run() {
        try {
            switch (target) {
                case "customers":
                    getCustomers();
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
        Cancellable subscription = client.newOrders().subscribe().with(
            System.out::println, // FIXME: why Log.info doesn't work here?
            Throwable::printStackTrace
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

    private void getCustomers() {
        List<Customer> customers = client.customers();
        Log.info(customers);
    }


}
