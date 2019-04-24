package com.example.metrics;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.concurrent.ThreadLocalRandom;

@Path("/gauge")
@ApplicationScoped
public class GaugeResource {

    @GET
    @Path("/")
    @Gauge(name = "annotated-gauge", absolute = true, unit = MetricUnits.NONE)
    @Produces("text/plain")
    public Long gauge() {
        return ThreadLocalRandom.current().nextLong();
    }

}

