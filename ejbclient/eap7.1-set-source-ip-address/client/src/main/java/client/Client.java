package client;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.wildfly.naming.client.WildFlyInitialContextFactory;

import ejb.HelloBeanRemote;

public class Client {

    public static void main(String[] args)
            throws Exception {
        InitialContext ctx = new InitialContext(getCtxProperties());
        try {
            String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote?stateful";
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
