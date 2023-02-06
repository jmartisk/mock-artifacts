package org.acme.shop.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends PanacheEntity {

    @Size(min = 4)
    private String name;

    @OneToMany
    private List<ShopOrder> orders = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShopOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ShopOrder> orders) {
        this.orders = orders;
    }

    public void addOrder(ShopOrder order) {
        this.orders.add(order);
        order.setUser(this);
    }
}
