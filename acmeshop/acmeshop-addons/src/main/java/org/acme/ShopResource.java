package org.acme;

import org.acme.model.Customer;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class ShopResource {

    @Query
    public Customer getCustomer(Long id) {
        Customer result = new Customer();
        result.setPassword("changeit");
        return result;
    }
}