package org.example.graphql.database;

import org.example.graphql.model.Gender;
import org.example.graphql.model.Person;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class Database {

    private List<Person> personList;

    @PostConstruct
    void init() {
        personList = new ArrayList<>();
        personList.add(new Person("Dave", Gender.MALE));
        personList.add(new Person("Mary", Gender.FEMALE));
        personList.add(new Person("Robotron", Gender.OTHER));

    }

    public List<Person> allPersons() {
        return Collections.unmodifiableList(personList);
    }

}
