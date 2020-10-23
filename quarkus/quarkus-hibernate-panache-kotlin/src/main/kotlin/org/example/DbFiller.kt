package org.example

import io.quarkus.runtime.StartupEvent
import org.example.model.Gender
import org.example.model.Person
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.transaction.Transactional

@ApplicationScoped
class DbFiller {

    @Transactional
    fun testingData(@Observes evt: StartupEvent) {
        val male = Gender()
        male.abbr = "M"
        male.description = "Male"
        male.persist()

        val female = Gender()
        female.abbr = "F"
        female.description = "Female"
        female.persist()

        val undefined = Gender()
        undefined.abbr = "U"
        undefined.description = "???"
        undefined.persist()

        val david = Person()
        david.gender = male
        david.persist()

        val james = Person()
        james.gender = male
        james.persist()

        val stephane = Person()
        stephane.gender = male
        stephane.persist()

        val mary = Person()
        mary.gender = female
        mary.persist()

        val dana = Person()
        dana.gender = female
        dana.persist()

        val frunubucator = Person()
        frunubucator.gender = undefined
        frunubucator.persist()
    }

}