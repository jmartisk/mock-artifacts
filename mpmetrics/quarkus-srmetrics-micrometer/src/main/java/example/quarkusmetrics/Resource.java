package example.quarkusmetrics;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.TimeUnit;

@Path("/")
public class Resource {

    @Counted(name = "xxcounter")
    @Path("/counter")
    @GET
    public void counter() {
        System.out.println("Count!");
    }

    @Timed(name = "xxtimer")
    @Path("/timer")
    @GET
    public void timer() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(5);
        System.out.println("Time!");
    }
}
