package org.acme.shop.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserDatabase {

    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
