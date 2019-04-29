package example.quarkusmetrics;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/inject")
@ApplicationScoped
public class InjectResource {

    @Inject
    @Metric(name = "injected-counter", absolute = true)
    private Counter counter;

    @GET
    @Path("/counter")
    public Long counter() {
        counter.inc();
        return counter.getCount();
    }

}
