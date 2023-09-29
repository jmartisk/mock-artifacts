package org.acme.shop.startup;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.runtime.StartupEvent;
import org.acme.shop.model.Customer;
import org.acme.shop.model.ShopOrder;
import org.acme.shop.model.Product;

import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

public class InitialData {

//    @WithTransaction
//    public void initialData(@Observes StartupEvent evt) {
//        Product soap = new Product();
//        soap.setName("Soap");
//        soap.persistAndFlush().s;
//
//        Customer alice = new Customer();
//        alice.setName("Alice");
//        alice.persistAndFlush();
//
//        ShopOrder alicesOrder = new ShopOrder();
//        alicesOrder.addItem(3, soap);
//        alice.addOrder(alicesOrder);
//        alicesOrder.persist();
//    }
}
