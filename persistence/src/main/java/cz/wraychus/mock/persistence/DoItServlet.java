package cz.wraychus.mock.persistence;

import cz.wraychus.mock.persistence.entity.User;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author jmartisk
 */
@WebServlet(urlPatterns = "/", name = "DoItServlet")
public class DoItServlet extends HttpServlet {

    @EJB
    ManagerEjb ejb;

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String username = UUID.randomUUID().toString();
        int numberOfEyes = (int)Math.round(Math.random() * 1000);
        ejb.createUser(username, numberOfEyes);
        User user = ejb.getUserByUsername(username);
        httpServletResponse.getWriter().append("<html><body>");
        httpServletResponse.getWriter().append(user.toString());
        httpServletResponse.getWriter().append("<br />Wanna see a <a href='oops'>rollback</a>?");
        httpServletResponse.getWriter().append("</body></html>");
    }


}
