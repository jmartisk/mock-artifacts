package example.remotejmx;

import java.util.Hashtable;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;
import org.wildfly.security.sasl.SaslMechanismSelector;

/**
 * @author Jan Martiska
 */
public class Main {

    public static void main(String[] args)
            throws Exception {
        JMXServiceURL jmxUrl = new JMXServiceURL("service:jmx:remote+http://127.0.0.1:9990");
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        createAuthContext("admin", "pass.1234").run(() -> {
            try (JMXConnector connector = JMXConnectorFactory.connect(jmxUrl, env)) {
                final Object version = connector.getMBeanServerConnection()
                        .getAttribute(new ObjectName("jboss.as:management-root=server"), "productVersion");
                System.out.println("Product version: " + version);
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static AuthenticationContext createAuthContext(String username, String password) {
        AuthenticationConfiguration userConf = AuthenticationConfiguration.empty()
                .setSaslMechanismSelector(SaslMechanismSelector.fromString("DIGEST-MD5"))
                .useName(username)
                .usePassword(password);
        return AuthenticationContext.empty().with(MatchRule.ALL, userConf);
    }
}
