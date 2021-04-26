package org.example.graphql;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.graphql.client.typesafe.api.GraphQlClientBuilder;
import org.example.graphql.model.Person;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Collection;

@Path("/")
public class ActionResource {

    @Inject
    PeopleClientApi client;

    @Path("/injected")
    @GET
    @Blocking
    public String callWithClient_injected() {
        Collection<Person> people = client.all();
        for (Person person : people) {
            System.out.println(person.getName() + " : " + person.getGender());
        }
        return "OK";
    }

    @Path("/manual")
    @GET
    @Blocking
    public String callWithClient_manual() {
        PeopleClientApi client = GraphQlClientBuilder.newBuilder().endpoint("http://localhost:8080/graphql").build(PeopleClientApi.class);
        Collection<Person> people = client.all();
        for (Person person : people) {
            System.out.println(person.getName() + " : " + person.getGender());
        }
        return "OK";
    }

}
