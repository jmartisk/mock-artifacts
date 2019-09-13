package example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloResource {

    @Path("/hello")
    @GET
    @Produces("text/plain")
    public Response hello() {
        return Response.status(200)
                .entity(new GenericEntity<>("Hello", String.class))
                .build();
    }

}
