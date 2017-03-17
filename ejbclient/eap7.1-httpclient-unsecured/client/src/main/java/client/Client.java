package client;

import java.net.URI;
import java.security.PrivilegedActionException;
import java.security.Provider;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.EJBClientConnection;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.protocol.remote.RemoteTransportProvider;
import org.wildfly.httpclient.common.WildflyHttpContext;
import org.wildfly.httpclient.ejb.HttpClientProvider;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;

import ejb.HelloBeanRemote;

/**
 * @author jmartisk
 */
public class Client {

    public static final String URL = "http://localhost:8080/wildfly-services";
    public static final String USERNAME = "joe";
    public static final String PASSWORD = "joeIsAwesome2013!";

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException {
        AuthenticationContext authCtx = AuthenticationContext
                .empty()
                .with(MatchRule.ALL,
                        AuthenticationConfiguration.EMPTY
                                .useName(USERNAME)
                                .usePassword(PASSWORD));
        AuthenticationContext.getContextManager().setGlobalDefault(authCtx);

        InitialContext iniCtx = new InitialContext(getCtxProperties());
        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)iniCtx.lookup(lookupName);
        System.out.println(bean.hello());
        iniCtx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, URL);
        return props;
    }

}
