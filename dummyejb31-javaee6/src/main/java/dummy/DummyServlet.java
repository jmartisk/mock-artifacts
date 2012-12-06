package dummy;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name = "MyAwesomeServlet", urlPatterns = "/")
public class DummyServlet extends HttpServlet {

    @EJB
    private SingletonSessionBean dummySingleton;

    @EJB
    private StatefulSessionBean dummyStateful;

    @EJB
    private StatelessSessionBean dummyStateless;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("SingletonSessionBean says: " + dummySingleton.sayHello("world"));
        out.println("StatefulSessionBean says: " + dummyStateful.sayHello("world"));
        out.println("StatelessSessionBean says: " + dummyStateless.sayHello("world"));
    }

}
