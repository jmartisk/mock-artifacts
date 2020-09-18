package org.example.graphql.client;

import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.client.model.Person;

import java.util.Collection;

@io.smallrye.graphql.client.typesafe.api.GraphQlClientApi
public interface PeopleClientApi {

    @Query(value = "all")
    Collection<Person> getAll();

}
