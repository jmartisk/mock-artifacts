package client;

import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.ejb.client.EJBClient;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import ejb.HelloBeanRemote;

/**
 * @author jmartisk
 */
public class Client {


    public static void main(String[] args)
            throws Exception {
        final InitialContext ejbCtx = new InitialContext(getProps());
        final HelloBeanRemote bean = (HelloBeanRemote)ejbCtx
                .lookup("ejb:/server/HelloBean!" + HelloBeanRemote.class.getName() + "?stateful");
        while (true) {
            try {
                System.out.println(bean.hello());
            } catch(Exception e) {
                System.out.println("Call failed!!!!");
                e.printStackTrace();
            }
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static Properties getProps() {
        final Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(EJBClient.CLUSTER_AFFINITY, "ejb"); //FIXME: needed until JBEAP-11124 is resolved
        return props;
    }

}
