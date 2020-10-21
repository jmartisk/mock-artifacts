package org.example.graphql.client.rest;

import io.smallrye.graphql.client.typesafe.api.GraphQlClientBuilder;
import org.example.graphql.client.api.PeopleClientApi;
import org.example.graphql.client.model.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collection;

@Path("/")
public class ClientWrappingApplication {

    @GET
    @Path("/all")
    @Produces({"application/json"})
    public Collection<Person> getAll() {
        Integer port = Integer.getInteger("port", 8080);
        String url = "http://localhost:" + port + "/graphql";
        System.out.println("Using URL " + url);
        PeopleClientApi client = GraphQlClientBuilder.newBuilder()
                .endpoint(url)
                .build(PeopleClientApi.class);
        return client.getAll();
    }
}
