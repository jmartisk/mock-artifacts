package org.example

import org.example.model.Person
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
class Api {

    @Path("/")
    @GET
    fun findAllPersons(): List<Person> {
        return Person.findAll().list()
    }

    @Path("/test")
    @GET
    fun countStats(): List<Person> {
        val entityManager = Person.entityManager()
        val resultList = entityManager.createQuery("select g.description, g.abbr, count(p) " +
                "from Person p " +
                "   inner join p.gender as g " +
                "group by g.abbr, g.description").resultList
        for (any in resultList) {
            val x: Array<Any> = any as Array<Any>
            println("${x[0]} ${x[1]} ${x[2]}")
        }
        return emptyList()
    }

}