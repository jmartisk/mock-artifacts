package com.example.metrics;

import org.eclipse.microprofile.metrics.annotation.Counted;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Greeter {

    @Counted(monotonic = true, absolute = true, name = "Greetings count")
    public String greet() {
        return "Howdy at " + LocalDateTime.now();
    }

}
