package com.example.metrics;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servlet", urlPatterns = "/*")
public class MyServlet extends HttpServlet {

    @Inject
    private BeanWithMetrics bean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        bean.countedMethod_bar();
        bean.countedMethod_bar();
        bean.countedMethod_baz();
    }

}
