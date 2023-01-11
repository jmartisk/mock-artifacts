package org.acme.shop.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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
