package org.acme;

import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.moderation.ModerationModel;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Booking;
import org.jboss.resteasy.reactive.RestQuery;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Path("/")
public class AiResource {

    @Inject
    BookingService bookingService;

    // just requesting to make sure they appear in devui
    @Inject
    ImageModel imageModel;

    // just requesting to make sure they appear in devui
    @Inject
    ModerationModel moderationModel;


    @Produces(MediaType.TEXT_PLAIN)
    @GET
    @Path("/create")
    public String create(@RestQuery String name, @RestQuery String email) {
        Booking booking = bookingService.createBooking(name, email, Date.from(Instant.now()),
                Date.from(Instant.now().plus(7, ChronoUnit.DAYS)));
        bookingService.sendEmail(email);
        return "ok";
    }

    @GET
    @Path("/image")
    public String image(@RestQuery String prompt) {
        return imageModel.generate(prompt).content().base64Data();
    }
}
