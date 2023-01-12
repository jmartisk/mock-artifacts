package org.acme.shop.client.model;

import java.math.BigInteger;
import java.util.List;

public class Customer {

    BigInteger id;

    List<ShopOrder> orders;

    String name;

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + id +
            ", orders=" + orders +
            ", name='" + name + '\'' +
            '}';
    }
}
