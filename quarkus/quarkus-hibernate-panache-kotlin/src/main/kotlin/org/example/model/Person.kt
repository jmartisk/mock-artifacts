package org.example.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table
class Person : PanacheEntity() {

    companion object : PanacheCompanion<Person, Long> {}

    @ManyToOne
    lateinit var gender: Gender

}