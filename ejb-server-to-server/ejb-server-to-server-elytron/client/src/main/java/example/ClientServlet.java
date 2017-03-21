package example;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
    private WhoAmIBeanRemote remoteBeanByInjection;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        final String hostname = System.getProperty("remote.ejb.host");
        if (hostname == null) {
            resp.getWriter().append("ERROR: please specify remote.ejb.host property");
            return;
        }

        // invoke bean obtained by injection (@EJB)
        try {
            String username = callBeanByInjection();
            resp.getWriter().append("WhoAmI from bean obtained by injection returned: ").append(username).append("\n");
        } catch (Exception e) {
            resp.getWriter().append("Calling bean obtained by injection failed: ");
            e.printStackTrace(resp.getWriter());
        }

        // invoke bean obtained by JNDI lookup
        try {
            String username = callBeanByLookup();
            resp.getWriter().append("WhoAmI from bean obtained by lookup returned: ").append(username).append("\n");
        } catch (Exception e) {
            resp.getWriter().append("Calling bean obtained by lookup failed: ");
            e.printStackTrace(resp.getWriter());
        }
    }

    public String callBeanByInjection() throws IOException {
        return remoteBeanByInjection.whoAmI();

    }

    public String callBeanByLookup() throws NamingException, IOException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");

        /* FIXME this shouldn't be necessary and all of this should be provided by the outbound connection + authentication context
          uncomment to make it work (but it's a workaround) */
        {
//            final String hostname = System.getProperty("remote.ejb.host");
//            Objects.requireNonNull(hostname, "Please specify the property remote.ejb.host");
//            props.put(Context.PROVIDER_URL, "http-remoting://" + hostname + ":8080");
//            props.put(Context.SECURITY_CREDENTIALS, "admin123+");
//            props.put(Context.SECURITY_PRINCIPAL, "admin");
        }

        InitialContext ctx = new InitialContext(props);
        WhoAmIBeanRemote remoteBeanByLookup = (WhoAmIBeanRemote)ctx
                .lookup("ejb:/server-side/WhoAmIBean!example.ejb.WhoAmIBeanRemote");
        return remoteBeanByLookup.whoAmI();
    }
}