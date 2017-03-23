package example;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jan Martiska
 */
@WebServlet(urlPatterns = "/", loadOnStartup = 1)
@ServletSecurity(
        @HttpConstraint(rolesAllowed = {"users"})
)
public class EJBServlet extends HttpServlet {

    @EJB
    private HelloEJB ejb;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().append(ejb.getUsername());
    }

}
