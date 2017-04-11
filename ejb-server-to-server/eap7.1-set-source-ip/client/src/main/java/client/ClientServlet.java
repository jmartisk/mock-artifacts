package client;

import ejb.HelloBeanRemote;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;


@WebServlet(urlPatterns = "/")
public class ClientServlet extends HttpServlet {

    @EJB(lookup = "ejb:/server-side/HelloBean!ejb.HelloBeanRemote?stateful")
    private HelloBeanRemote remoteBeanByInjection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // invoke bean obtained by injection (@EJB)
        try {
            String info = callBeanByInjection();
            resp.getWriter().append("HelloBean from bean obtained by injection returned: ").append(info).append("\n");
        } catch (Exception e) {
            resp.getWriter().append("Calling bean obtained by injection failed: ");
            e.printStackTrace(resp.getWriter());
        }

        // invoke bean obtained by JNDI lookup
        try {
            String info = callBeanByLookup();
            resp.getWriter().append("HelloBean from bean obtained by lookup returned: ").append(info).append("\n");
        } catch (Exception e) {
            resp.getWriter().append("Calling bean obtained by lookup failed: ");
            e.printStackTrace(resp.getWriter());
        }
    }

    public String callBeanByInjection() throws IOException {
        return remoteBeanByInjection.hello();

    }

    public String callBeanByLookup() throws NamingException, IOException {
        Properties props = new Properties();
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        InitialContext ctx = new InitialContext(props);
        HelloBeanRemote remoteBeanByLookup = (HelloBeanRemote) ctx
                .lookup("ejb:/server-side/HelloBean!ejb.HelloBeanRemote?stateful");
        return remoteBeanByLookup.hello();
    }
}


