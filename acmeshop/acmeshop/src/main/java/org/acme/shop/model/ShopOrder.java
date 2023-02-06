package org.acme.shop.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShopOrder extends PanacheEntity {

    @ManyToOne
    private Customer customer;

    @OneToMany
    private List<OrderItem> items = new ArrayList<>();

    public Customer getUser() {
        return customer;
    }

    public void setUser(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItem(Integer quantity, Product product) {
        OrderItem item = new OrderItem();
        item.setQuantity(quantity);
        item.setProduct(product);
        item.persistAndFlush();
        this.items.add(item);
    }
}
