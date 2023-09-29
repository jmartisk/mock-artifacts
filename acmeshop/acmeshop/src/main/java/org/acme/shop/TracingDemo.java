package org.acme.shop;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.acme.shop.model.OrderItemInput;
import org.acme.shop.model.Product;
import org.acme.shop.model.ShopOrder;
import org.jboss.resteasy.reactive.RestQuery;

import java.util.List;

@Path("/")
public class TracingDemo {

    @POST
    @Path("/product/create")
    @WithTransaction
    public Uni<Product> createProduct(@RestQuery Long id) {
        Product order = new Product();
        return order.persistAndFlush();
    }

}
