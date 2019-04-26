package com.example.metrics;

import javax.ws.rs.Path;

@Path("/parameter")
public class ParametersResource {

    // TODO: enable after fixing https://github.com/smallrye/smallrye-metrics/issues/107
    // otherwise, to make this work, remove counters from ProducersFieldsResource and ProducersMethodsResource

/*

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

*/

}
