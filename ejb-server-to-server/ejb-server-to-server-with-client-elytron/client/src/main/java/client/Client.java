package client;

import java.security.PrivilegedActionException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.wildfly.naming.client.WildFlyInitialContextFactory;

import example.ejb.WhoAmIBeanRemote;

/**
 * @author jmartisk
 */
public class Client {

    public static void main(String[] args) throws NamingException, PrivilegedActionException {
        InitialContext ctx = new InitialContext(getCtxProperties());
        String lookupName = "ejb:/intermediate/Intermediate!example.ejb.WhoAmIBeanRemote";
        WhoAmIBeanRemote bean = (WhoAmIBeanRemote)ctx.lookup(lookupName);
        System.out.println("The target bean was invoked as user " + bean.whoAmI());
        ctx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        final String url = "remote+http://" + System.getProperty("intermediate.ejb.host") + ":8080";
        System.out.println("Connecting to " + url);
        props.put(Context.PROVIDER_URL, url);
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.SECURITY_PRINCIPAL, "joe");
        props.put(Context.SECURITY_CREDENTIALS, "joeIsAwesome2013!");
        return props;
    }

}
