package org.example.graphql.api

import org.example.graphql.model.Person

object PeopleDatabase {

    val people: MutableList<Person> = mutableListOf()

    fun add(person: Person) {
        people.add(person)
    }

    fun getAll(): Collection<Person> {
        return people
    }
}