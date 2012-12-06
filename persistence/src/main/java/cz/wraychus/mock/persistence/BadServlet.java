package cz.wraychus.mock.persistence;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jmartisk
 * @since 12/6/12
 */
@WebServlet(urlPatterns = "/oops", name = "BadServlet")
public class BadServlet extends HttpServlet{

    @EJB
    ManagerEjb ejb;

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ejb.rollbackATransaction();
    }
}
