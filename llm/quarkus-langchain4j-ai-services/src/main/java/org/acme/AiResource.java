package org.acme;

import dev.langchain4j.model.image.ImageModel;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/")
public class AiResource {

    @Inject
    PoetService poetService;

    @Inject
    ImageModel imageModel;

    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/poem/{topic}/{lines}")
    public String poem(@PathParam("topic") String topic, @PathParam("lines") Integer lines) {
        poetService.writeAPoem(topic, lines);
        return poetService.sendEmail("Poem", "test@test.com");
    }

    @GET
    @Path("/image")
    public String image(@RestQuery String prompt) {
        return imageModel.generate(prompt).content().base64Data();
    }
}
