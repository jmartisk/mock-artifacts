package cz.wraychus.mock.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DummyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("You have to stay in shape. My grandmother, she started walking " +
                "five miles a day when she was 60. She’s 97 today and we don’t know where the hell she is.");
        out.flush();
    }
}
