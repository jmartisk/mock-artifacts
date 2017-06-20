package client;

import java.net.URI;
import java.security.PrivilegedActionException;
import java.security.Provider;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.Affinity;
import org.jboss.ejb.client.ClusterAffinity;
import org.jboss.ejb.client.EJBClient;
import org.jboss.ejb.client.EJBClientCluster;
import org.jboss.ejb.client.EJBClientConnection;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.EJBIdentifier;
import org.jboss.ejb.client.StatefulEJBLocator;
import org.jboss.ejb.client.StatelessEJBLocator;
import org.jboss.ejb.client.URIAffinity;
import org.jboss.ejb.protocol.remote.RemoteTransportProvider;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;
import org.wildfly.security.sasl.SaslMechanismSelector;

import ejb.HelloBeanRemote;

/**
 * @author jmartisk
 */
public class Client {

    final static String URL = "remote+http://127.0.0.1:8080";

    public static void main(String[] args)
            throws Exception {
       /* // FIXME try to get rid of programmatic context builder
        final EJBClientContext ctx = new EJBClientContext.Builder()
                .addTransportProvider(new RemoteTransportProvider())
                .addClientCluster(
                        new EJBClientCluster.Builder().setName("ejb").build()
                )
                .addClientConnection(
                        new EJBClientConnection.Builder()
                                .setDestination(URI.create(URL))
                                .build()
                ).build();
        EJBClientContext.getContextManager().setGlobalDefault(ctx);
*/
        final InitialContext ejbCtx = new InitialContext(getProps());
        final HelloBeanRemote bean = (HelloBeanRemote)ejbCtx
                .lookup("ejb:/server/HelloBean!" + HelloBeanRemote.class.getName() + "?stateful");

        // FIXME shouldn't need to set the affinity myself
        final StatefulEJBLocator<HelloBeanRemote> locator = EJBClient
                .createSession(Affinity.NONE, HelloBeanRemote.class, "", "server", "HelloBean", "");
        final HelloBeanRemote beanWithAffinity = EJBClient.createProxy(locator);
//        EJBClient.setStrongAffinity(beanWithAffinity, new ClusterAffinity("ejb"));

        while (true) {
            System.out.println(beanWithAffinity.hello());
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static Properties getProps() {
        final Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return props;
    }

}
