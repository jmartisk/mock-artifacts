package org.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import java.util.List;
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

    @GET
    @Path("{segment}/{other}/{segment}/list")
    public Response list(@PathParam("segment") List<PathSegment> segments) {
        System.out.println("list segments: " + segments.size());
        return Response.ok().build();
    }

    @GET
    @Path("{segment}/{other}/{segment}/array")
    public Response array(@PathParam("other") String other, @PathParam("segment") PathSegment[] segments) {
        System.out.println("array segments: " + segments.length);
        return Response.ok().build();
    }

    @GET
    @Path("{segment}/{other}/{segment}/varargs")
    public Response varargs(@PathParam("other") String other, @PathParam("segment") PathSegment... segments) {
        System.out.println("array segments: " + segments.length);
        return Response.ok().build();
    }

    @Path("/async")
    @GET
    public CompletionStage<String> async() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                int timeout = ThreadLocalRandom.current().nextInt(1000);
                TimeUnit.MILLISECONDS.sleep(timeout);
                System.out.println("Waited " + timeout + " ms");
                return "Hello";
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Path("/async/exception")
    @GET
    public CompletionStage<String> asyncException() {
        return CompletableFuture.supplyAsync(() -> {
            int timeout = ThreadLocalRandom.current().nextInt(1000);
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Waited " + timeout + " ms");
            throw new RuntimeException("!!!");
        });
    }

}
