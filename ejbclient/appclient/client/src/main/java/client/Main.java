package client;

import java.security.PrivilegedActionException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.EJBClientContext;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import ejb.HelloBeanRemote;

public class Main {

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException {
        EJBClientContext.getCurrent().getConfiguredConnections().forEach(connection -> System.out.println(connection.getDestination()));
        InitialContext ctx = new InitialContext(getCtxProperties());
        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
        System.out.println(bean.hello());
        ctx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return props;
    }

}
