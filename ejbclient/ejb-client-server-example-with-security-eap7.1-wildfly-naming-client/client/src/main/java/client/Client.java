package client;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.HelloBeanRemote;

/**
 * @author jmartisk
 * @since 7/3/13
 */
public class Client {

    public static void main(String[] args) throws NamingException {
        InitialContext ctx = new InitialContext(getCtxProperties());
        String lookupName = "/server/HelloBean!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
        System.out.println(bean.hello());
        ctx.close();
    }

    public static Properties getCtxProperties() {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        env.put("jboss.naming.client.ejb.context", "true");
        env.put(Context.SECURITY_PRINCIPAL, "joe");
        env.put(Context.SECURITY_CREDENTIALS, "joeIsAwesome2013!");
        return env;
    }

}
