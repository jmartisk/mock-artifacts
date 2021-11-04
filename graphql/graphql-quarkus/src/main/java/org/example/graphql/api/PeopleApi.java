package org.example.graphql.api;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;
import org.example.graphql.model.Gender;
import org.example.graphql.model.Person;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@GraphQLApi
@ApplicationScoped
public class PeopleApi {

    private List<Person> database = new ArrayList<>();

    // a new random person is generated each second
    private Multi<Person> newPersons;

    @PostConstruct
    void init() {
        database.add(new Person("david", Gender.MALE));
        database.add(new Person("jane", Gender.FEMALE));
        newPersons = Multi.createFrom()
                .ticks()
                .every(Duration.ofMillis(150))
                .map(number -> new Person("person" + number, Gender.OTHER))
                .invoke(person -> database.add(person))
                .broadcast()
                .toAllSubscribers();
        // optionally, show the generated persons in stdout
//        newPersons.subscribe().with(item -> { System.out.println("New person: " + item.getName()); });
    }

    // To try out, see queries/query-all-persons* files
    @Query(value = "all")
    @Description("Retrieve all persons from the database")
    public Collection<Person> all_methodName() {
        return database;
    }

    // To try out, see queries/mutation-create-person* files
    @Mutation(value = "create")
    @Description("Create a person")
    public Person create_methodName(Person person) {
        database.add(person);
        return person;
    }

    @Query(value = "uni")
    public Uni<Person> randomPerson() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        return Uni.createFrom()
            .item(new Person("random" + ThreadLocalRandom.current().nextInt(2000), Gender.MALE));
    }

    // one Multi shared by all clients
    @Subscription
    public Multi<Person> newPeopleShared() {
        return newPersons;
    }

    // each client gets their own Multi
    @Subscription
    public Multi<Person> newPeople() {
//        return Multi.createFrom().failure(new RuntimeException("blabla"));       // if failure is desired
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofMillis(150))
                .map(number -> new Person("person" + number, Gender.OTHER))
                .invoke(person -> database.add(person));
    }

    // This effectively adds a "secretToken" field to the Person type. It is random and different each time it is requested.
    // To try out, see queries/generate-secret-tokens* files
    @Name("secretToken")
    public String generateSecretToken(@Source Person person,
                                      @DefaultValue("true")
                                      @Name("maskFirstPart") boolean maskFirstPart) {
        String uuid = UUID.randomUUID().toString();
        // inserting an error?
//        if(person.getName().endsWith("1")) {
//            throw new RuntimeException("Unknown token");
//        }
        if (maskFirstPart) {
            return uuid.substring(0, uuid.length() - 4).replaceAll("[A-Za-z0-9]", "*")
                    + uuid.substring(uuid.length() - 4);
        }
        else {
            return uuid;
        }
    }

}
