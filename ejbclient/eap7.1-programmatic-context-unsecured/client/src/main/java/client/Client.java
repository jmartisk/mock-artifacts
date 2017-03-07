package client;

import java.security.PrivilegedActionException;
import java.security.Provider;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.protocol.remote.RemoteTransportProvider;
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

    public static void main(String[] args) throws NamingException, PrivilegedActionException {
        AuthenticationConfiguration common = AuthenticationConfiguration.EMPTY
                .useProviders(() -> new Provider[] {new WildFlyElytronProvider()})
                .allowAllSaslMechanisms();
        AuthenticationContext authCtxEmpty = AuthenticationContext.empty();
        final AuthenticationContext authCtx = authCtxEmpty.with(MatchRule.ALL, common);

        AuthenticationContext.getContextManager().setThreadDefault(authCtx);

        InitialContext ctx = new InitialContext(getCtxProperties());
        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
        System.out.println(bean.hello());
        ctx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080");
        return props;
    }

}
