package dummy;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: jmartisk
 * Date: 6/28/12
 * Time: 2:52 PM
 */
public class DummyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StatelessSessionBean stateless;
        StatefulSessionBean stateful;
        try {
            InitialContext ctx = new InitialContext();
            stateless = (StatelessSessionBean)(ctx.lookup("stateless"));
            stateful = (StatefulSessionBean)(ctx.lookup("stateful"));
        } catch (NamingException e) {
            throw new ServletException(e);
        }
        PrintWriter out = response.getWriter();
        out.println("StatefulSessionBean says: " + stateful.sayHello("world"));
        out.println("StatelessSessionBean says: " + stateless.sayHello("world"));
        out.flush();
    }

}
