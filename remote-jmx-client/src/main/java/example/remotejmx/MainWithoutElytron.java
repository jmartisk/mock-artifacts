package example.remotejmx;

import java.util.Hashtable;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;

/**
 * @author Jan Martiska
 */
public class MainWithoutElytron {

    public static void main(String[] args)
            throws Exception {
        JMXServiceURL jmxUrl = new JMXServiceURL("service:jmx:remote+http://127.0.0.1:9990");
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        String[] credentials = new String[] { "admin", "pass.1234" };
        env.put(JMXConnector.CREDENTIALS, credentials);
        try(JMXConnector connector = JMXConnectorFactory.connect(jmxUrl, env)) {
            final Object version = connector.getMBeanServerConnection()
                    .getAttribute(new ObjectName("jboss.as:management-root=server"), "productVersion");
            System.out.println("Product version: " + version);
        }


    }
}
