package client;

import java.security.PrivilegedActionException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.wildfly.naming.client.WildFlyInitialContextFactory;

import ejb.HelloBeanRemote;

public class Client {

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException {
        final String addr = System.getProperty("remote.server.address", "127.0.0.1");
        final Boolean stateful = Boolean.getBoolean("stateful");
        final String URL = "http://" + addr + ":8080/wildfly-services";
        InitialContext iniCtx = new InitialContext(getCtxProperties(URL));
        String lookupName = stateful ?
                "ejb:/server/HelloBeanStateful!ejb.HelloBeanRemote?stateful" :
                "ejb:/server/HelloBeanStateless!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)iniCtx.lookup(lookupName);
        try {
            for (int i = 0; i < 10_000; i++) {
                System.out.println(bean.hello());
                TimeUnit.SECONDS.sleep(1);
            }
        } finally {
            iniCtx.close();
        }
    }

    public static Properties getCtxProperties(String URL) {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, URL);
        return props;
    }

}
