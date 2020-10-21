package org.example.graphql.api

import org.eclipse.microprofile.graphql.*
import org.example.graphql.model.Person
import java.util.*
import javax.enterprise.context.ApplicationScoped

@GraphQLApi
@ApplicationScoped
class PeopleApi {

    // To try out, see queries/query-all-persons* files
    @Query(value = "all")
    @Description("Retrieve all persons from the database")
    fun all_methodName(): Collection<Person> {
        return PeopleDatabase.getAll()
    }

    // To try out, see queries/mutation-create-person* files
    @Mutation(value = "create")
    @Description("Create a person")
    fun create_methodName(person: Person): Person {
        PeopleDatabase.add(person)
        return person
    }

    // This effectively adds a "secretToken" field to the Person type. It is random and different each time it is requested.
    // To try out, see queries/generate-secret-tokens* files
    @Name("secretToken")
    fun generateSecretToken(@Source person: Person?,
                            @DefaultValue("true") @Name("maskFirstPart") maskFirstPart: Boolean): String {
        val uuid = UUID.randomUUID().toString()
        return if (maskFirstPart) {
            (uuid.substring(0, uuid.length - 4).replace("[A-Za-z0-9]".toRegex(), "*")
                    + uuid.substring(uuid.length - 4))
        } else {
            uuid
        }
    }

}