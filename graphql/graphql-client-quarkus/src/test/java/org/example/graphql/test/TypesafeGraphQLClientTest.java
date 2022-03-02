package org.example.graphql.test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.example.graphql.PeopleClientApi;
import org.example.graphql.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Duration;

/**
 * !!!! THIS WILL FAIL IF THE SERVER SIDE APP IS NOT RUNNING AT localhost:8080
 */
@QuarkusTest
public class TypesafeGraphQLClientTest {

    @Inject
    PeopleClientApi client;

    @Test
    public void testRandomPerson() {
        UniAssertSubscriber<Person> subscriber = new UniAssertSubscriber<>();
        client.randomPerson().subscribe().withSubscriber(subscriber);
        subscriber.awaitItem(Duration.ofSeconds(5));
        subscriber.assertTerminated();
        Person item = subscriber.getItem();
        Assertions.assertNotNull(item);
        Assertions.assertNotNull(item.getName());
        Assertions.assertNotNull(item.getGender());
    }

}
