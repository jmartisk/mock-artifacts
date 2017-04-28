package client;

import java.net.URI;
import java.security.PrivilegedActionException;
import java.security.Provider;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.ejb.client.EJBClientConnection;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.protocol.remote.RemoteTransportProvider;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import org.wildfly.security.WildFlyElytronProvider;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.AuthenticationContextConfigurationClient;
import org.wildfly.security.auth.client.MatchRule;

import ejb.TransactionalBeanRemote;

/**
 * @author jmartisk
 */
public class Client {

    public static void main(String[] args)
            throws NamingException, PrivilegedActionException, InterruptedException, SystemException,
            NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {

        final EJBClientConnection.Builder connectionBuilder = new EJBClientConnection.Builder();
        final URI uri = URI.create("remote+http://127.0.0.1:8080");
        connectionBuilder.setDestination(uri);
        final EJBClientContext.Builder builder = new EJBClientContext.Builder();
        builder.addClientConnection(connectionBuilder.build());
        builder.addTransportProvider(new RemoteTransportProvider());
        EJBClientContext.getContextManager().setGlobalDefault(builder.build());

        final AuthenticationContext authCtx = authCtx();    // FIXME this shouldn't be necessary
        AuthenticationContext.getContextManager().setGlobalDefault(authCtx);

        InitialContext ctx = new InitialContext(getCtxProperties());
        InitialContext ctxWithoutAffinity = new InitialContext(propsJustFactory());
        final UserTransaction tx = (UserTransaction)ctx.lookup("txn:UserTransaction");

        // create an entity and roll back
        String lookupName = "ejb:/server/TransactionalBean!ejb.TransactionalBeanRemote";
        TransactionalBeanRemote bean = (TransactionalBeanRemote)ctxWithoutAffinity.lookup(lookupName);  // FIXME this should work with 'ctx' as well
        System.out.println("Number of committed entities: " + bean.getEntitiesCount());
        System.out.println("Beginning a transaction...");
        tx.begin();     // FIXME
//        authCtx.run(() -> {
//            try {
//                tx.begin();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });

        System.out.println("Creating an entity...");
        bean.createEntity();
        System.out.println("Rolling back the transaction...");

//        tx.rollback();   // FIXME
        authCtx.run(() -> {
            try {
                tx.rollback();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Number of committed entities: " + bean.getEntitiesCount());

        System.out.println("Beginning a transaction...");

//        tx.begin();     // FIXME
        authCtx.run(() -> {
            try {
                tx.begin();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Creating an entity...");
        bean.createEntity();
        System.out.println("Committing the transaction...");

//        tx.commit(); // FIXME
        authCtx.run(() -> {
            try {
                tx.commit();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Number of committed entities: " + bean.getEntitiesCount());

        ctx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080");
//        props.put(Context.SECURITY_PRINCIPAL, "joe");
//        props.put(Context.SECURITY_CREDENTIALS, "joeIsAwesome2013!");
        return props;
    }

    public static Properties propsJustFactory() {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return props;
    }

    public static AuthenticationContext authCtx() {
        AuthenticationConfiguration userConf = AuthenticationConfiguration.EMPTY
                .useProviders(() -> new Provider[] {new WildFlyElytronProvider()})
                .allowSaslMechanisms("DIGEST-MD5")
                .useName("joe")
                .usePassword("joeIsAwesome2013!")
                .useRealm("ApplicationRealm");
        return AuthenticationContext.empty().with(MatchRule.ALL, userConf);
    }

}
