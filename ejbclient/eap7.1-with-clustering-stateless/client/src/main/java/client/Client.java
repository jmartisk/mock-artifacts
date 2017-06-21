package client;

import java.security.PrivilegedActionException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.wildfly.naming.client.WildFlyInitialContextFactory;

import ejb.HelloBeanRemote;

/**
 * @author jmartisk
 */
public class Client {

    final static String URL = "remote+http://127.0.0.1:8080";

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException {
        final InitialContext ejbCtx = new InitialContext(getProps());
        final HelloBeanRemote bean = (HelloBeanRemote)ejbCtx
                .lookup("ejb:/server/HelloBean!" + HelloBeanRemote.class.getName());
        while (true) {
            System.out.println(bean.hello());
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static Properties getProps() {
        final Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return props;
    }

}
