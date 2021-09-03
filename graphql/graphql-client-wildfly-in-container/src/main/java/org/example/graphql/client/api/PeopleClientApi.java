package org.example.graphql.client.api;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.client.model.Person;

import java.util.Collection;

@GraphQLClientApi
public interface PeopleClientApi {

    @Query(value = "all")
    Collection<Person> getAll();

}
