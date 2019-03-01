package com.example.metrics;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servlet", urlPatterns = "/*")
public class MyServlet extends HttpServlet {

    @Inject
    private Greeter greeter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(greeter.greet());
    }

}
