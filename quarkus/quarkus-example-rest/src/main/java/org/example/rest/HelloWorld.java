package org.example.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloWorld {

    @GET
    @Path("/")
    public String hello() {
        return "hello";
    }

}
