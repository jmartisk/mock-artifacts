package com.example.metrics;

import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;
import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Path("/")
public class MetricResource {

    @GET
    @Path("/counter")
    @Counted(name = "blabla", absolute = true)
    @Produces("text/plain")
    public String countedMethod() {
        return "ACK";
    }

    @GET
    @Path("/cgauge")
    @Produces("text/event-stream")
    public void cGauge(@Context SseEventSink sseEventSink, @Context Sse sse) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        // run 10 threads, each will execute for 0-14 seconds
        CompletableFuture[] array = IntStream.range(0, 10)
                .parallel()
                .mapToObj(i -> cGaugedMethod(sseEventSink, sse))
                .toArray(CompletableFuture[]::new);
        pool.shutdown();
        CompletableFuture.allOf(array)
                .thenRun(sseEventSink::close);
    }

    @ConcurrentGauge(name = "cgauge", absolute = true)
    public CompletionStage<Void> cGaugedMethod(SseEventSink sseEventSink, Sse sse) {
        CompletableFuture<Void> ret = new CompletableFuture<>();
        sseEventSink.send(sse.newEvent(Thread.currentThread().getName() + " start"));
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(15));
        } catch (InterruptedException e) {
            // nada
        } finally {
            sseEventSink
                    .send(sse.newEvent(Thread.currentThread().getName() + " finish"))
                    .thenRun(() -> ret.complete(null));
        }
        return ret;
    }

}
