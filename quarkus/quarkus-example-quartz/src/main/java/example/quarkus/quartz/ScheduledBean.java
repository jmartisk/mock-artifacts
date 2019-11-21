package example.quarkus.quartz;

import io.quarkus.scheduler.Scheduled;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class ScheduledBean {

    private AtomicLong ticks;

    @PostConstruct
    public void init() {
        ticks = new AtomicLong();
    }

    @Scheduled(cron = "*/1 * * * * ?")
    public void tick() {
        System.out.println("Tick: " + ticks.incrementAndGet());
    }

    public Long getTicks() {
        return ticks.get();
    }
}
