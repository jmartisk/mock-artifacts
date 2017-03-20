package example;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import example.ejb.WhoAmIBeanRemote;

/**
 * @author Jan Martiska
 */
@WebServlet(urlPatterns = "/")
public class ClientServlet extends HttpServlet {

    @EJB(lookup = "ejb:/server-side/WhoAmIBean!example.ejb.WhoAmIBeanRemote")
    private WhoAmIBeanRemote remoteBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final String username = remoteBean.whoAmI();
        System.out.println("WhoAmI returned: " + username);
        resp.getWriter().append(username);
    }
}