package example.quarkusmetrics;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class MainResource {

    @Inject
    private MetricRegistry registry;

    @GET
    @Produces("text/plain")
    @Path("/")
    public Long counter() {
        Metadata metadata = new Metadata("counter", MetricType.COUNTER);
        metadata.setReusable(true);
        Counter counter = registry.counter(metadata);
        counter.inc();
        return counter.getCount();
    }

}