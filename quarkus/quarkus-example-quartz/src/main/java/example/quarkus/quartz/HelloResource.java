package example.quarkus.quartz;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloResource {

    @Inject
    ScheduledBean scheduledBean;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Long hello() {
        return scheduledBean.getTicks();
    }
}