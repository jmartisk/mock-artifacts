package com.example.metrics;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Path("/")
public class MetricResource {

    @GET
    @Path("/counter")
    @Counted(name = "annotated-counter", absolute = true, monotonic = true)
    @Produces("text/plain")
    public String countedMethod() {
        return "Ok";
    }

    @GET
    @Path("/timer")
    @Timed(name = "annotated-timer", absolute = true, reusable = true)
    @Produces("text/plain")
    public String timedMethod1() throws InterruptedException {
        int milliseconds = ThreadLocalRandom.current().nextInt(500);
        TimeUnit.MILLISECONDS.sleep(milliseconds);
        return "Ok, waited " + milliseconds + " ms";
    }

    @GET
    @Path("/timer2")
    @Timed(name = "annotated-timer", absolute = true, reusable = true)
    @Produces("text/plain")
    public String timedMethod2() throws InterruptedException {
        int milliseconds = ThreadLocalRandom.current().nextInt(500) + 500;
        TimeUnit.MILLISECONDS.sleep(milliseconds);
        return "Ok, waited " + milliseconds + " ms";
    }

    @GET
    @Path("/meter")
    @Metered(name = "annotated-meter", absolute = true)
    @Produces("text/plain")
    public String meteredMethod() throws InterruptedException {
        int milliseconds = ThreadLocalRandom.current().nextInt(500);
        TimeUnit.MILLISECONDS.sleep(milliseconds);
        return "Ok, waited " + milliseconds + " ms";
    }


}
