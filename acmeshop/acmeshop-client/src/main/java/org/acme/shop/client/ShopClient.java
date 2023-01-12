package org.acme.shop.client;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.mutiny.Multi;
import org.acme.shop.client.model.Customer;
import org.acme.shop.client.model.Product;
import org.acme.shop.client.model.ShopOrder;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

@GraphQLClientApi(configKey = "shopTypesafe")
public interface ShopClient {

    @Query
    List<Customer> customers();

    @Query
    List<Product> products();

    @Subscription
    Multi<ShopOrder> newOrders();

}

