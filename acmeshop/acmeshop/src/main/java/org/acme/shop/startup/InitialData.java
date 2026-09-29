package org.acme.shop.startup;

import io.quarkus.runtime.StartupEvent;
import jakarta.inject.Inject;
import org.acme.shop.model.User;

import jakarta.enterprise.event.Observes;
import org.acme.shop.model.UserDatabase;

public class InitialData {

    @Inject
    UserDatabase userDatabase;

    public void initialData(@Observes StartupEvent evt) {
        User user = new User("admin", "admin@admin.org");
        userDatabase.getUsers().add(user);
    }
}
