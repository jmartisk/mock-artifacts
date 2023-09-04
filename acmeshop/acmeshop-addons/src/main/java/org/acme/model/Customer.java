package org.acme.model;

import io.smallrye.graphql.api.federation.Key;

@Key(fields = "id")
public class Customer {

    private Long id;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
