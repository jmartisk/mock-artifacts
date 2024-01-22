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
    public List<Customer> getCustomers() {
        return Customer.listAll();
    }

    @Query
    public List<ShopOrder> getOrders() {
        return ShopOrder.listAll();
    }
    @Query
    public List<Product> getProducts() {
        return Product.listAll();
    }

    @Mutation
    @Transactional
    public Customer createCustomer(@Valid Customer customer) {
        customer.persist();
        return customer;
    }

    @Mutation
    @Transactional
    public ShopOrder createOrder(Long customerId, List<OrderItemInput> itemInput) {
        ShopOrder order = new ShopOrder();
        List<OrderItem> items = itemInput.stream()
            .map(input -> {
                OrderItem item = new OrderItem();
                item.setProduct(Product.findById(input.getProductId()));
                item.setQuantity(input.getQuantity());
                item.persistAndFlush();
                return item;
            }).toList();
        order.setItems(items);
        order.persistAndFlush();
        Customer.<Customer>findById(customerId).addOrder(order);
        newOrdersPublisher.onNext(order);
        return order;
    }

    private Multi<ShopOrder> newOrdersMulti;
    private BroadcastProcessor<ShopOrder> newOrdersPublisher;

    @PostConstruct
    public void initializeNewOrdersMulti() {
        newOrdersPublisher = BroadcastProcessor.create();
        newOrdersMulti = Multi.createFrom().publisher(newOrdersPublisher);
    }

    @Subscription
    public Multi<ShopOrder> newOrders() {
        return newOrdersMulti;
    }
}