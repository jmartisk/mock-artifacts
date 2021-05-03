package org.example.graphql;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.model.Person;

import java.util.Collection;

@GraphQLClientApi(configKey = "people")
public interface PeopleClientApi {

    @Query(value = "all")
    Collection<Person> all();

}
