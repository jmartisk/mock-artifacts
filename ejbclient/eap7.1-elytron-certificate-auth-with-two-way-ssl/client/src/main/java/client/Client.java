package client;

import java.security.Security;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.wildfly.security.WildFlyElytronProvider;

import ejb.HelloBeanRemote;

public class Client {

    public static void main(String[] args) throws NamingException {
        Security.addProvider(new WildFlyElytronProvider());
        InitialContext ctx = new InitialContext(getCtxProperties());
        try {
            String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
            HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
            System.out.println("The username is: " + bean.hello());
        } finally {
            ((Context)ctx.lookup("ejb:")).close();
            ctx.close();
        }
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        return props;
    }

}
