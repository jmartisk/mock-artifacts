package client;

import java.security.PrivilegedActionException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;

import ejb.HelloBeanRemote;

public class Client {

    public static final String URL = "http://localhost:8080/wildfly-services";
    public static final String USERNAME = "joe";
    public static final String PASSWORD = "joeIsAwesome2013!";

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException {
        InitialContext iniCtx = new InitialContext(getCtxProperties());
        String lookupName;
        HelloBeanRemote bean;
        try {
            lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
            bean = (HelloBeanRemote) iniCtx.lookup(lookupName);
            System.out.println("Stateless bean said: " + bean.whoami());

            lookupName = "ejb:/server/HelloBeanStateful!ejb.HelloBeanRemote?stateful";
            bean = (HelloBeanRemote) iniCtx.lookup(lookupName);
            System.out.println("Stateful bean said: " + bean.whoami());
        } finally {
            iniCtx.close();
        }
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, URL);
        props.put(Context.SECURITY_PRINCIPAL, USERNAME);
        props.put(Context.SECURITY_CREDENTIALS, PASSWORD);
        return props;
    }

}
