package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloResource {
    @Path("/")
    @GET
    public String hello() {
        return "Hello";
    }

}
