package client;

import java.net.URI;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.Provider;
import java.util.Properties;
import javax.ejb.EJB;
import javax.management.RuntimeErrorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.EJBClientCluster;
import org.jboss.ejb.client.EJBClientConnection;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.EJBClientInterceptor;
import org.jboss.ejb.client.EJBIdentifier;
import org.jboss.ejb.client.EJBTransportProvider;
import org.jboss.ejb.client.StatelessEJBLocator;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;

import ejb.HelloBeanRemote;

/**
 * @author jmartisk
 * @since 7/3/13
 */
public class Client {

    public static void main(String[] args) throws NamingException, PrivilegedActionException {

        AuthenticationConfiguration common = AuthenticationConfiguration.EMPTY
                .useProviders(() -> new Provider[] { new WildFlyElytronProvider() })
                .allowSaslMechanisms("DIGEST-MD5")
                .useRealm("ApplicationRealm");
        AuthenticationContext authCtxEmpty = AuthenticationContext.empty();
        AuthenticationConfiguration joe = common.useName("joe").usePassword("joeIsAwesome2013!");
        final AuthenticationContext authCtx = authCtxEmpty.with(MatchRule.ALL, joe);

        final EJBClientContext.Builder ejbClientBuilder = new EJBClientContext.Builder();

        final EJBClientConnection.Builder connectionBuilder = new EJBClientConnection.Builder();
        connectionBuilder.setDestination(URI.create("http-remoting://127.0.0.1:8080"));

        ejbClientBuilder.addClientConnection(connectionBuilder.build());
        final EJBClientContext ejbCtx = ejbClientBuilder.build();

        EJBClientContext.getContextManager().setGlobalDefault(ejbCtx);
        EJBClientContext.getContextManager().setThreadDefault(ejbCtx);

        ejbCtx.run(() -> {
            try {
                authCtx.run((PrivilegedExceptionAction<Void>)() -> {
                    try {
                        InitialContext ctx = new InitialContext(getCtxProperties());
                        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
                        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
                        System.out.println(bean.hello());
                        ctx.close();
                        return null;
                    } catch (NamingException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (PrivilegedActionException e) {
                throw new RuntimeException(e);
            }
        });


    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return props;
    }

}
