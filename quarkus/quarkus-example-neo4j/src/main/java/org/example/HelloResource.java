package org.example;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Path("/")
public class HelloResource {

    @Inject
    Driver driver;

    @Path("/create")
    @GET
    public String create() {
        try(Session session = driver.session()) {
            long id = ThreadLocalRandom.current().nextLong(0, 10000000);
            session.run("CREATE (n: User {id:" + id +"})");
            return "created user " + id;
        }
    }

    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Long> get() {
        try(Session session = driver.session()) {
            Result result = session.run("MATCH (n)" +  // get all nodes in the graph
                    "  RETURN n");
            return result.list()
                    .stream()
                    .map(record -> {
                        return record.get("n").get("id").asLong();
                    })
                    .collect(Collectors.toList());
        }
    }

}
