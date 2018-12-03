package com.example.metrics;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@WebServlet(name = "servlet", urlPatterns = "/*")
public class MyServlet extends HttpServlet {

    @Inject
    private LongRunningBean greeter;

    @Resource
    ManagedScheduledExecutorService ses;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Starting long running actions...");
        ses.scheduleAtFixedRate(() -> {
            new Thread(() -> greeter.longRunningAction()).start();
        }, 0, 1, TimeUnit.SECONDS);
    }

}
