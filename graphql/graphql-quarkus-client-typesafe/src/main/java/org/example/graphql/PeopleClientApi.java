package org.example.graphql;

import io.smallrye.graphql.client.typesafe.api.GraphQlClientApi;
import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.model.Person;

import java.util.Collection;

@GraphQlClientApi(configKey = "people")
public interface PeopleClientApi {

    @Query(value = "all")
    Collection<Person> all();

}
