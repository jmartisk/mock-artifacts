package org.example;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class NumberGenerator {

    private static AtomicLong count = new AtomicLong(1);

    @CircuitBreaker
    public Long generateANumber() {
        long currentCount = count.getAndIncrement();
        if (currentCount % 3 == 0) {
            return ThreadLocalRandom.current().nextLong(0, 100);
        } else {
            throw new RuntimeException("Number generator failed");
        }
    }

}
