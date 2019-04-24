package com.example.metrics;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;

@Path("/inject")
@ApplicationScoped
public class InjectResource {

    // TODO: enable after fixing https://github.com/smallrye/smallrye-metrics/issues/107
   /* @Inject
    @Metric(name = "injected-counter", absolute = true)
    private Counter counter;

    @GET
    @Path("/counter")
    public Long counter() {
        counter.inc();
        return counter.getCount();
    }*/

}
