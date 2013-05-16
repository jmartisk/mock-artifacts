package mock;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author jmartisk
 * @since 5/16/13
 */
@Stateless
@Path("/bean")
public class RestBean {

    @GET
    @Path("/hello/{name}")
    public String hello(@PathParam(value = "name") String name) {
        return "hello " + name;
    }
}
