package org.example.graphql;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.model.Person;
import org.example.graphql.model.PersonWithSourceError;

import java.util.Collection;

@GraphQLClientApi(configKey = "people")
public interface PeopleClientApi {

    @Query(value = "all")
    Collection<Person> all();

    @Subscription
    Multi<Person> newPeople();

    @Subscription(value = "newPeople")
    Multi<PersonWithSourceError> newPeopleWithSourceError();

    @Query(value = "uni")
    Uni<Person> randomPerson();

}
