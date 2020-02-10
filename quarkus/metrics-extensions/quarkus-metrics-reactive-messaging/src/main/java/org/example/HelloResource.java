package org.example;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloResource {

    @Inject
    @Channel("strings")
    Emitter<String> emitter;

    @Path("/")
    @GET
    public void hello() {
        emitter.send("a");
        emitter.send("b");
        emitter.send("c");
        emitter.send("d");
    }

}
