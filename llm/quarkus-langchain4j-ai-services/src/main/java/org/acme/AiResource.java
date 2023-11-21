package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class AiResource {

    @Inject
    PoetService poetService;

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/poem/{topic}/{lines}")
    public String poem(@PathParam("topic") String topic, @PathParam("lines") Integer lines) {
        poetService.writeAPoem(topic, lines);
        return poetService.sendEmail("Poem", "test@test.com");
    }

}
