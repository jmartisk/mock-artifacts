package client;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;
import org.wildfly.security.sasl.SaslMechanismSelector;

import ejb.TransactionalBeanRemote;

/**
 * @author jmartisk
 */
public class Client {

    public static final String USERNAME = "joe";
    public static final String PASSWORD = "joeIsAwesome2013!";
    public static final String URL = "remote+http://127.0.0.1:8080";

    public static void main(String[] args) throws Exception {
        // variant 1
        runUsingSecurityCredentialsInInitialContext();

        // variant 2
        runUsingAuthenticationContext();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, URL);
        return props;
    }

    public static Properties getCtxPropertiesWithSecurityCredentials() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, URL);
        props.put(Context.SECURITY_PRINCIPAL, USERNAME);
        props.put(Context.SECURITY_CREDENTIALS, PASSWORD);
        return props;
    }

    public static void runUsingSecurityCredentialsInInitialContext() throws NamingException {
        System.out.println("******************* running using security credentials in Context properties");
        execute(new InitialContext(getCtxPropertiesWithSecurityCredentials()));
    }

    public static void runUsingAuthenticationContext() throws NamingException {
        System.out.println("******************* running using authentication context");
        final InitialContext ctx = new InitialContext(getCtxProperties());
        createAuthCtx().run(() -> execute(ctx));
    }

    public static AuthenticationContext createAuthCtx() {
        AuthenticationConfiguration userConf = AuthenticationConfiguration.empty()
                .setSaslMechanismSelector(SaslMechanismSelector.fromString("DIGEST-MD5"))
                .useName(USERNAME)
                .usePassword(PASSWORD)
                .useRealm("ApplicationRealm");
        return AuthenticationContext.empty().with(MatchRule.ALL, userConf);
    }

    private static void execute(InitialContext ctx) {
        try {
            String lookupName = "ejb:/server/TransactionalBean!ejb.TransactionalBeanRemote";
            final TransactionalBeanRemote bean = (TransactionalBeanRemote)ctx.lookup(lookupName);
            final UserTransaction tx = (UserTransaction)ctx.lookup("txn:UserTransaction");
            System.out.println("Number of committed entities: " + bean.getEntitiesCount());
            System.out.println("Beginning a transaction...");
            tx.begin();
            System.out.println("Creating an entity...");
            bean.createEntity();
            System.out.println("Rolling back the transaction...");
            tx.rollback();
            System.out.println("Number of committed entities: " + bean.getEntitiesCount());
            System.out.println("Beginning a transaction...");
            tx.begin();
            System.out.println("Creating an entity...");
            bean.createEntity();
            System.out.println("Committing the transaction...");
            tx.commit();
            System.out.println("Number of committed entities: " + bean.getEntitiesCount());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ctx.close();
            } catch (NamingException e) {
            }
        }
    }

}
