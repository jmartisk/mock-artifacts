package org.acme.shop;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonValue;
import org.acme.shop.model.User;
import org.acme.shop.model.UserDatabase;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;
import java.util.UUID;

@GraphQLApi
public class ShopGraphQLResource {

    @Inject
    UserDatabase userDatabase;

    @Query
    public List<User> getUsers() {
        return userDatabase.getUsers();
    }

    @Blocking
    @Subscription
    public Multi<User> getRandomUsers() {
        return Multi.createFrom()
                .range(0, 10)
                .map(x -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new User(UUID.randomUUID().toString(), UUID.randomUUID().toString() + "@example.org");
                }).runSubscriptionOn(Infrastructure.getDefaultWorkerPool());
    }

}