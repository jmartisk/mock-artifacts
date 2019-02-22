package com.example.metrics;

import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class BeanWithMetrics {

    @Counted(name = "countedMethod", absolute = true, tags = {"foo=bar"})
    public void countedMethod_bar() {
        System.out.println("BeanWithMetrics.countedMethod_bar");
    }

    @Counted(name = "countedMethod", absolute = true, tags = {"foo=baz"})
    public void countedMethod_baz() {
        System.out.println("BeanWithMetrics.countedMethod_baz");
    }

}
