package org.example.graphql.api;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;
import org.example.graphql.model.Gender;
import org.example.graphql.model.Person;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@GraphQLApi
@RequestScoped
public class PeopleApi {

    private List<Person> database = new ArrayList<>();

    // to check the user using EE Security API
//    @Inject
//    javax.security.enterprise.SecurityContext securityContext;

    @PostConstruct
    void init() {
        database.add(new Person("david", Gender.MALE));
        database.add(new Person("jane", Gender.FEMALE));
    }

    // To try out, see queries/query-all-persons* files
    @Query(value = "all")
    @Description("Retrieve all persons from the database")
    public Collection<Person> all() {

        // to check the user using EE Security API
//        java.security.Principal callerPrincipal = securityContext.getCallerPrincipal();
//        System.out.println("EE API principal: " + (callerPrincipal != null ? callerPrincipal.getName() : "null"));

        // to check the user using Elytron API
        org.wildfly.security.auth.server.SecurityIdentity identity
                = org.wildfly.security.auth.server.SecurityDomain.getCurrent()
                .getCurrentSecurityIdentity();
        System.out.println("Name: " + identity.getPrincipal().getName());
        System.out.println("Roles:");
        identity.getRoles().forEach(System.out::println);

        return database;
    }

    @Query(value = "allUni")
    @Description("Retrieve all persons from the database")
    public Uni<Collection<Person>> allUni() {
        Supplier<Collection<Person>> supplier = () -> database;
        return Uni.createFrom().item(supplier);
    }

    // To try out, see queries/mutation-create-person* files
    @Mutation(value = "create")
    @Description("Create a person")
    public Person create_methodName(Person person) {
        database.add(person);
        return person;
    }

    // This effectively adds a "secretToken" field to the Person type. It is random and different each time it is requested.
    // To try out, see queries/generate-secret-tokens* files
    @Name("secretToken")
    public String generateSecretToken(@Source Person person,
                                      @DefaultValue("true")
                                      @Name("maskFirstPart") boolean maskFirstPart) {
        String uuid = UUID.randomUUID().toString();
        if (maskFirstPart) {
            return uuid.substring(0, uuid.length() - 4).replaceAll("[A-Za-z0-9]", "*")
                    + uuid.substring(uuid.length() - 4);
        }
        else {
            return uuid;
        }
    }

}
