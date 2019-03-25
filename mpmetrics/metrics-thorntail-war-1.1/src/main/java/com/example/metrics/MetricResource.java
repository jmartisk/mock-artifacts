package com.example.metrics;

import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
@ApplicationScoped
public class MetricResource {

    @GET
    @Path("/counter")
    @Counted(name = "blabla", absolute = true, monotonic = true)
    @Produces("text/plain")
    public String countedMethod() {
        return "ACK";
    }

}
