package example.quarkusmetrics;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.metrics.MetricRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class Initializer {

    @Inject
    MetricRegistry registry;

    public void registerSomeMetrics(@Observes StartupEvent event) {
        System.out.println("Registering some metrics through MP Metrics Registry Adapter");
        registry.counter("blabla").inc();
    }
}
