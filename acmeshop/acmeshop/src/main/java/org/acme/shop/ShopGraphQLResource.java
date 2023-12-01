package org.acme.shop;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import org.acme.shop.model.Customer;
import org.acme.shop.model.OrderItem;
import org.acme.shop.model.OrderItemInput;
import org.acme.shop.model.Product;
import org.acme.shop.model.ShopOrder;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;

@GraphQLApi
public class ShopGraphQLResource {

    @Query
    public MyCustomScalar exec() {
        return new MyCustomScalar();
    }
}