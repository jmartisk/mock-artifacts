package org.example;

import org.infinispan.Cache;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.metrics.impl.ApplicationMetricsRegistry;
import org.infinispan.metrics.impl.ApplicationMetricsRegistryFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.function.BiFunction;

@Path("/")
public class HelloResource {

    @Inject
    EmbeddedCacheManager emc;

    @Path("/")
    @GET
    public String hello() {
        final Cache<Object, Object> cache = emc.getCache("test");
        System.out.println(cache);
        cache.put("k", "hello");
        return (String)cache.get("k");
    }

}
