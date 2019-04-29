package example.quarkusmetrics;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/parameter")
public class ParametersResource {

    @Inject
    MetricBean metricBean;

    @Path("/counter")
    @GET
    public Long counter() {
        return metricBean.getCounter();
    }

    @RequestScoped
    public static class MetricBean {

        private Counter counter;

        @Inject
        private void construct(@Metric(name = "parameter-counter", absolute = true) Counter counter) {
            this.counter = counter;
            counter.inc();
        }

        public Long getCounter() {
            return counter.getCount();
        }

    }


}
