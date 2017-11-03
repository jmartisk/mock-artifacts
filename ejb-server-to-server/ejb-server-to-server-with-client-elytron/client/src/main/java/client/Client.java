package client;

import java.net.URI;
import java.security.PrivilegedActionException;
import java.security.Provider;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import example.ejb.WhoAmIBeanRemote;
import org.jboss.ejb.client.EJBClientConnection;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.protocol.remote.RemoteTransportProvider;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;

/**
 * @author jmartisk
 */
public class Client {

    public static void main(String[] args) throws NamingException, PrivilegedActionException {
        AuthenticationConfiguration common = AuthenticationConfiguration.empty();
        AuthenticationContext authCtxEmpty = AuthenticationContext.empty();
        final AuthenticationContext authCtx = authCtxEmpty.with(MatchRule.ALL, common);

        final EJBClientContext.Builder ejbClientBuilder = new EJBClientContext.Builder();
        ejbClientBuilder.addTransportProvider(new RemoteTransportProvider());
        final EJBClientConnection.Builder connBuilder = new EJBClientConnection.Builder();
        System.out.println("remote+http://" + System.getProperty("intermediate.ejb.host") + ":8080");
        connBuilder.setDestination(URI.create("remote+http://" + System.getProperty("intermediate.ejb.host") + ":8080"));
        ejbClientBuilder.addClientConnection(connBuilder.build());
        final EJBClientContext ejbCtx = ejbClientBuilder.build();

        AuthenticationContext.getContextManager().setThreadDefault(authCtx);
        EJBClientContext.getContextManager().setThreadDefault(ejbCtx);

        InitialContext ctx = new InitialContext(getCtxProperties());
        String lookupName = "ejb:/intermediate/Intermediate!example.ejb.WhoAmIBeanRemote";
        WhoAmIBeanRemote bean = (WhoAmIBeanRemote)ctx.lookup(lookupName);
        System.out.println(bean.whoAmI());
        ctx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return props;
    }

}
