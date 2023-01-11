package org.acme.shop.startup;

import io.quarkus.runtime.StartupEvent;
import org.acme.shop.model.Customer;
import org.acme.shop.model.ShopOrder;
import org.acme.shop.model.Product;

import javax.enterprise.event.Observes;
import javax.transaction.Transactional;

public class InitialData {

    @Transactional
    public void initialData(@Observes StartupEvent evt) {
        Product soap = new Product();
        soap.setName("Soap");
        soap.persistAndFlush();

        Customer alice = new Customer();
        alice.setName("Alice");
        alice.persistAndFlush();

        ShopOrder alicesOrder = new ShopOrder();
        alicesOrder.addItem(3, soap);
        alice.addOrder(alicesOrder);
        alicesOrder.persist();
    }
}
