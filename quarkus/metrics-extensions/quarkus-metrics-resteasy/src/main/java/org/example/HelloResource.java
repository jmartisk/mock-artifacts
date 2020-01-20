package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Path("/")
public class HelloResource {

    @Path("/hello/{name}")
    @GET
    public String hello(@PathParam("name") String name) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(100));
        return "hello " + name;
    }

    @Path("/error")
    @GET
    public Response errorResponse() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(200));
        return Response.serverError().build();
    }

    @Path("/exception")
    @GET
    public Long exception() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(300));
        throw new RuntimeException("Yep!");
    }

    @Path("/async")
    @GET
    public CompletionStage<String> async() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
                return "Hello";
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
