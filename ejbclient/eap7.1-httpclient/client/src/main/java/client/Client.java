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
        AuthenticationContext authCtx = AuthenticationContext
                .empty()
                .with(MatchRule.ALL,
                        AuthenticationConfiguration.EMPTY
                                .useName(USERNAME)
                                .usePassword(PASSWORD));
        AuthenticationContext.getContextManager().setGlobalDefault(authCtx);

        InitialContext iniCtx = new InitialContext(getCtxProperties());
        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)iniCtx.lookup(lookupName);
        System.out.println(bean.hello());
        iniCtx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, URL);
        return props;
    }

}
