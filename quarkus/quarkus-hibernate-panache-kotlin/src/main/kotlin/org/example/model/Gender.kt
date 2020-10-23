package org.example.model

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table
class Gender : PanacheEntity() {

    companion object : PanacheCompanion<Gender, Long> {}

    lateinit var abbr: String

    lateinit var description: String

}