package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class HelloResource {

    @Path("/")
    @GET
    public String hello() {
        throw new RuntimeException();
//        return "Hello";
    }

}
