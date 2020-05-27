package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Path("/rest")
public class RESTService {

    @Path("/fast")
    @GET
    public void fastmethod() {

    }

    @Path("/slow")
    @GET
    public void slowmethod() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(300));
    }

}
