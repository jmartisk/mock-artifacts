package org.example.graphql.client;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.client.model.Person;

import java.io.Closeable;
import java.util.Collection;

@GraphQLClientApi
public interface PeopleClientApi  extends Closeable {

    @Query(value = "all")
    Collection<Person> getAll();

}
