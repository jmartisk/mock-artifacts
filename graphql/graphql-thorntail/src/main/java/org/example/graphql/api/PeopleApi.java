package org.example.graphql.api;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.database.Database;
import org.example.graphql.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@GraphQLApi
@ApplicationScoped
public class PeopleApi {

    @Inject
    Database database;

    @Query(value = "allPersons")
    @Description("Retrieve all persons from the database")
    public Collection<Person> allPersons() {
        new Exception().printStackTrace();
        return database.allPersons();
    }

}
