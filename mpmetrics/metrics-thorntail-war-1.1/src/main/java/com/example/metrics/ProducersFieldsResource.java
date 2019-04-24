package com.example.metrics;

import io.smallrye.metrics.app.CounterImpl;
import io.smallrye.metrics.app.ExponentiallyDecayingReservoir;
import io.smallrye.metrics.app.HistogramImpl;
import io.smallrye.metrics.app.MeterImpl;
import io.smallrye.metrics.app.TimerImpl;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Gauge;
import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.Meter;
import org.eclipse.microprofile.metrics.Timer;
import org.eclipse.microprofile.metrics.annotation.Metric;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Path("/producerfield")
@ApplicationScoped
public class ProducersFieldsResource {

    @Produces
    @Metric(name = "producerfield-counter", absolute = true, description = "Counter from producer field.")
    Counter counter = new CounterImpl();

    @Produces
    @Metric(name = "producerfield-histogram", absolute = true, description = "Histogram from producer field.")
    Histogram histogram = new HistogramImpl(new ExponentiallyDecayingReservoir());

    @Produces
    @Metric(name = "producerfield-meter", absolute = true, description = "Meter from producer field.")
    Meter meter = new MeterImpl();

    @Produces
    @Metric(name = "producerfield-timer", absolute = true, description = "Timer from producer field.")
    Timer timer = new TimerImpl();

    @Produces
    @Metric(name = "producerfield-gauge", absolute = true, description = "Gauge from producer field.")
    Gauge<Long> gauge = () -> ThreadLocalRandom.current().nextLong(100);

    @GET
    @Path("/counter")
    public void counter() {
        counter.inc();
    }

    @GET
    @Path("/histogram")
    public void histogram() {
        histogram.update(ThreadLocalRandom.current().nextInt(100));
    }

    @GET
    @Path("/meter")
    public void meter() {
        meter.mark(1);
    }

    @GET
    @Path("/timer")
    public void timer() {
        timer.update(ThreadLocalRandom.current().nextInt(100), TimeUnit.MILLISECONDS);
    }

    @GET
    @Path("/gauge")
    public Long gauge() {
        return gauge.getValue();
    }

}
