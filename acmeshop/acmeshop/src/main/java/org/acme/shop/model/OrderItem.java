package org.acme.shop.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem extends PanacheEntity {

    private Integer quantity;

    @ManyToOne
    private Product product;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer count) {
        this.quantity = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
