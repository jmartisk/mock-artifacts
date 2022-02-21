package org.example.graphql;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.Cancellable;
import org.example.graphql.model.Person;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.Duration;
import java.util.Collection;

@Path("/typesafe")
public class TypesafeResource {

    private Logger LOG = Logger.getLogger(TypesafeResource.class.getName());

    @Inject
    PeopleClientApi client;

    @Path("/query")
    @GET
    @Blocking
    public void query() {
        Collection<Person> people = client.all();
        for (Person person : people) {
            System.out.println(person.getName() + " : " + person.getGender());
        }
    }

    Cancellable subscription;

    @Path("/subscription/start")
    @GET
    public void subscriptionStart() {
        Multi<Person> multi = client.newPeople();
        subscription = multi.subscribe().with(item -> {
            LOG.info("Typesafe client received: " + item);
        }, failure -> {
            LOG.warn("Typesafe client received failure", failure);
        }, () -> {
            LOG.info("Typesafe client's subscription finished");
        });
    }

    @Path("/subscription/stop")
    @GET
    public void subscriptionStop() {
        subscription.cancel();
    }

    @Path("/subscription/failing")
    @GET
    public void subscriptionFailing() {
        client.failingMulti().subscribe().with(item -> {
            LOG.info("Typesafe client received: " + item);
        }, failure -> {
            LOG.warn("Typesafe client received failure", failure);
        }, () -> {
            LOG.info("Typesafe client's subscription finished");
        });
    }

    // TODO
//    @Path("/subscription-with-source-error")
//    @GET
//    @SseElementType(value = "text/plain")
//    @Produces(MediaType.SERVER_SENT_EVENTS)
//    public Multi<String> subscription_withSourceError() {
//        Multi<PersonWithSourceError> multi = client.newPeopleWithSourceError();
//        return multi.map(PersonWithSourceError::toString);
//    }

    @Path("/uni")
    @GET
    @Blocking
    public void callUni() {
        System.out.println("BEFORE SENDING REQUEST");
        Uni<Person> personUni = client.randomPerson();
        System.out.println("SENT REQUEST");
        Person person = personUni.await().atMost(Duration.ofSeconds(5));
        System.out.println("REQUEST DONE");
        System.out.println(person.getName() + " : " + person.getGender());
    }

}
