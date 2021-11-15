package org.example.graphql;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.graphql.client.typesafe.api.ErrorOr;
import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.example.graphql.model.Person;
import org.example.graphql.model.PersonWithSourceError;
import org.jboss.resteasy.annotations.SseElementType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
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

    @Path("/subscription")
    @GET
    @SseElementType(value = "text/plain")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> callWithClient_injected_subscription() {
        Multi<Person> multi = client.newPeople();
        return multi.map(Object::toString);
    }

    @Path("/subscription-with-source-error")
    @GET
    @SseElementType(value = "text/plain")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> subscription_withSourceError() {
        Multi<PersonWithSourceError> multi = client.newPeopleWithSourceError();
        return multi.map(PersonWithSourceError::toString);
    }

    @Path("/uni")
    @GET
    @Blocking
    public String callWithClient_injected_uni() {
        System.out.println("BEFORE SENDING REQUEST");
        Uni<Person> personUni = client.randomPerson();
        System.out.println("SENT REQUEST");
        Person person = personUni.await().atMost(Duration.ofSeconds(5));
        System.out.println("REQUEST DONE");
        System.out.println(person.getName() + " : " + person.getGender());
        return "OK";
    }

    @Path("/manual")
    @GET
    @Blocking
    public String callWithClient_manual() {
        PeopleClientApi client = TypesafeGraphQLClientBuilder.newBuilder().endpoint("http://localhost:8080/graphql").build(PeopleClientApi.class);
        Collection<Person> people = client.all();
        for (Person person : people) {
            System.out.println(person.getName() + " : " + person.getGender());
        }
        return "OK";
    }

}
