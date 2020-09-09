package example.quarkusmetrics;

import org.eclipse.microprofile.metrics.Histogram;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;

@Path("/")
public class MainResource {

    private LongAccumulator highestPrimeSoFar = new LongAccumulator(Long::max, 2);

    @Gauge(name = "highestPrimeNumberSoFar", unit = MetricUnits.NONE, description = "Highest prime number so far.")
    public Long highestPrimeNumberSoFar() {
        return highestPrimeSoFar.get();
    }

    @Inject
    MetricRegistry registry;

    @Inject
    @Metric(name = "histogram")
    Histogram histogram;

    @GET
    @Produces("text/plain")
    @Path("/counter")
    @Counted(name = "counter", absolute = true, tags = "foo=bar")
    public String counter() {
        highestPrimeSoFar.accumulate(ThreadLocalRandom.current().nextInt(10000));
        System.out.println("VALUE == =" + highestPrimeSoFar.get());
        return "OK";
    }

//    @GET
//    @Produces("text/plain")
//    @Path("/gauge")
//    @Gauge(name = "gauge", absolute = true, unit = MetricUnits.NONE)
//    public Long gauge() {
//        return ThreadLocalRandom.current().nextLong(100);
//    }

    @GET
    @Produces("text/plain")
    @Path("/timer")
    @Timed(name = "timer", absolute = true)
    public String timer() throws InterruptedException {
        System.out.println("VALUE = " + highestPrimeSoFar.get() + " on " + this.toString());
        long wait = ThreadLocalRandom.current().nextLong(1000);
        TimeUnit.MILLISECONDS.sleep(wait);
        return "OK, waited " + wait + " milliseconds";
    }

    @GET
    @Produces("text/plain")
    @Path("/timer2")
    @Timed(name = "timer", absolute = true, tags="gg=yy")
    public String timer2() throws InterruptedException {
        long wait = ThreadLocalRandom.current().nextLong(1000);
        TimeUnit.MILLISECONDS.sleep(wait);
        return "OK, waited " + wait + " milliseconds";
    }

    @GET
    @Produces("text/plain")
    @Path("/simpletimer")
    @SimplyTimed(name = "simpletimer", absolute = true)
    public String simpleTimer() throws InterruptedException {
        long wait = ThreadLocalRandom.current().nextLong(1000);
        TimeUnit.MILLISECONDS.sleep(wait);
        return "OK, waited " + wait + " milliseconds";
    }

    @GET
    @Produces("text/plain")
    @Path("/simpletimer2")
    @SimplyTimed(name = "simpletimer", absolute = true, tags="bla=foo")
    public String simpleTimer2() throws InterruptedException {
        long wait = ThreadLocalRandom.current().nextLong(1000);
        TimeUnit.MILLISECONDS.sleep(wait);
        return "OK, waited " + wait + " milliseconds";
    }

    @GET
    @Produces("text/plain")
    @Path("/histogram")
    public String histogram() {
        long number = ThreadLocalRandom.current().nextLong(1000);
        histogram.update(number);
        return "OK, added " + number + " to the histogram";
    }

    static class Dummy {
        private String bla;

        public Dummy(String bla) {
            this.bla = bla;
        }

        public String getBla() {
            return bla;
        }

        public void setBla(String bla) {
            this.bla = bla;
        }
    }

    @GET
    @Produces("application/json")
    @Path("/meter")
    @Metered(name = "meter", absolute = true)
    public Dummy meter() throws InterruptedException {
        long wait = ThreadLocalRandom.current().nextLong(1000);
        TimeUnit.MILLISECONDS.sleep(wait);
        return new Dummy("adfadf");
    }

    @GET
    @Produces("text/plain")
    @Path("/meter2")
    @Metered(name = "meter", absolute = true, tags={"a=b"})
    public String meter2() throws InterruptedException {
        long wait = ThreadLocalRandom.current().nextLong(1000);
        TimeUnit.MILLISECONDS.sleep(wait);
        return "OK, waited " + wait + " milliseconds";
    }

    @GET
    @Produces("text/plain")
    @Path("/cgauge")
    @ConcurrentGauge(name = "cgauge", absolute = true)
    public String cgauge() throws InterruptedException {
        System.out.println("sleeping...");
//        TimeUnit.SECONDS.sleep(10);
        return "OK, slept for 10 seconds";
    }

    @GET
    @Produces("text/plain")
    @Path("/cgauge2")
    @ConcurrentGauge(name = "cgauge", absolute = true, tags={"a=b"})
    public String cgauge2() throws InterruptedException {
        System.out.println("sleeping...");
//        TimeUnit.SECONDS.sleep(10);
        return "OK, slept for 10 seconds";
    }


}