package org.example.graphql.model

class Person {

    lateinit var name: String

    lateinit var gender: Gender

    constructor()

    constructor(name: String, gender: Gender) {
        this.name = name
        this.gender = gender
    }

}