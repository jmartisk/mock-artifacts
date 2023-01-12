package org.acme.shop.client.model;

import java.math.BigInteger;
import java.util.List;

public class ShopOrder {

    BigInteger id;

    List<OrderItem> items;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShopOrder{" +
            "id=" + id +
            ", items=" + items +
            '}';
    }
}
