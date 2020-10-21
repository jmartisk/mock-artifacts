package org.example.graphql.client.api;

import io.smallrye.graphql.client.typesafe.api.GraphQlClientApi;
import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.client.model.Person;

import java.util.Collection;

@GraphQlClientApi
public interface PeopleClientApi {

    @Query(value = "all")
    Collection<Person> getAll();

}
