package com.example.metrics;

import org.eclipse.microprofile.metrics.annotation.ConcurrentGauge;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class LongRunningBean {

    @ConcurrentGauge(absolute = true,
            name = "Example conc gauge",
            tags = "tag1=value1")
    public void longRunningAction() {
        try {
            System.out.println("start");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextLong(10));
            System.out.println("finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
