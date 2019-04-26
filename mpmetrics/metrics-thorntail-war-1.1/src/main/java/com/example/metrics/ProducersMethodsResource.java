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

@Path("/producermethod")
@ApplicationScoped
public class ProducersMethodsResource {

    private Gauge<Long> gaugeValue = () -> ThreadLocalRandom.current().nextLong(100);

//    private Counter counterValue = new CounterImpl();

    private Histogram histogramValue = new HistogramImpl(new ExponentiallyDecayingReservoir());

    private Timer timerValue = new TimerImpl();

    private Meter meterValue = new MeterImpl();

    @Produces
    @Metric(name = "producermethod-gauge", description = "Gauge from producer method.", absolute = true)
    Gauge<Long> gauge() {
        return gaugeValue;
    }

//    @Produces
//    @Metric(name = "producermethod-counter", description = "Counter from producer method.", absolute = true)
//    Counter counter() {
//        return counterValue;
//    }

    @Produces
    @Metric(name = "producermethod-timer", description = "Timer from producer method.", absolute = true)
    Timer timer() {
        return timerValue;
    }

    @Produces
    @Metric(name = "producermethod-histogram", description = "Histogram from producer method.", absolute = true)
    Histogram histogram() {
        return histogramValue;
    }

    @Produces
    @Metric(name = "producermethod-meter", description = "Meter from producer method.", absolute = true)
    Meter meter() {
        return meterValue;
    }

    @GET
    @Path("/histogram")
    public void callHistogram() {
        histogram().update(ThreadLocalRandom.current().nextInt(100));
    }

    @GET
    @Path("/gauge")
    public Long callGauge() {
        return gauge().getValue();
    }

    @GET
    @Path("/meter")
    public void callMeter() {
        meter().mark(1);
    }

//    @GET
//    @Path("/counter")
//    public void callCounter() {
//        counter().inc();
//    }

    @GET
    @Path("/timer")
    public void callTimer() {
        timer().update(ThreadLocalRandom.current().nextInt(100), TimeUnit.MILLISECONDS);
    }
}
