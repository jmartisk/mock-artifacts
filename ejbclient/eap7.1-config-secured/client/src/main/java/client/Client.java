package client;

import java.security.PrivilegedActionException;
import java.security.Security;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.Affinity;
import org.jboss.ejb.client.EJBClient;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.WildFlyElytronProvider;

import ejb.HelloBeanRemote;

public class Client {

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException {
        Security.addProvider(new WildFlyElytronProvider());       // FIXME this is a workaround and should not be needed
        InitialContext ctx = new InitialContext(getCtxProperties());
        try {
            String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote?stateful";
            HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
            EJBClient.setStrongAffinity(bean, Affinity.NONE);       // FIXME this is needed for DR16
            for(int i = 0; i<20; i++) {
                System.out.println(bean.hello());
                TimeUnit.SECONDS.sleep(1);
            }
        } finally {
            ctx.close();
        }
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return props;
    }

}
