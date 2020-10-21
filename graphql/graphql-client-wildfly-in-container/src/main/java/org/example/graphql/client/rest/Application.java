package org.example.graphql.client.rest;

import javax.ws.rs.ApplicationPath;
import java.util.Collections;
import java.util.Set;

@ApplicationPath("/")
public class Application  extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(ClientWrappingApplication.class);
    }
}
