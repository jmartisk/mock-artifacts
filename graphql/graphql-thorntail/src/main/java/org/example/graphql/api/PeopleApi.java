package org.example.graphql.api;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.example.graphql.database.Database;
import org.example.graphql.model.Gender;
import org.example.graphql.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;

@GraphQLApi
@ApplicationScoped
public class PeopleApi {

    @Inject
    Database database;

    @Query(value = "all")
    @Description("Retrieve all persons from the database")
    public Collection<Person> all_methodName() {
        return database.allPersons();
    }

    @Mutation(value = "create")
    @Description("Create a person")
    // TODO: allow passing a whole person including gender
    public Person create_methodName(@Name("name") String name) {
        Person person = new Person(name, Gender.OTHER);
        database.addPerson(person);
        return person;
    }

}
