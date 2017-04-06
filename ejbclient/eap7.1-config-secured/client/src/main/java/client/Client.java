package client;

import java.security.PrivilegedActionException;
import java.security.Security;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.WildFlyElytronProvider;

import ejb.HelloBeanRemote;

public class Client {

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException {
        Security.addProvider(new WildFlyElytronProvider());       // FIXME this is a workaround and should not be needed
        InitialContext ctx = new InitialContext(getCtxProperties());
        try {
            String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
            HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
            System.out.println(bean.hello());
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
