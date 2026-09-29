package org.acme.shop.client;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.mutiny.Multi;
import org.acme.shop.client.model.User;
import org.eclipse.microprofile.graphql.Query;

@GraphQLClientApi(configKey = "shopTypesafe")
public interface ShopClient {

    @Query
    User getUsers();

    @Subscription
    Multi<User> randomUsers();

}

