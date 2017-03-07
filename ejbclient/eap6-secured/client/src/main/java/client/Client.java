package client;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.HelloBeanRemote;

/**
 * @author jmartisk
 */
public class Client {

    public static void main(String[] args) throws NamingException {
        InitialContext ctx = new InitialContext(getCtxProperties());
        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
        System.out.println(bean.hello());
        ctx.close();
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put("org.jboss.ejb.client.scoped.context", true);
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        props.put("remote.connections", "main");
        props.put("remote.connection.main.host", "localhost");
        props.put("remote.connection.main.port", "4447");
        props.put("remote.connection.main.username", "joe");
        props.put("remote.connection.main.password", "joeIsAwesome2013!");
        props.put("remote.connection.main.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS","true");
        props.put("remote.connection.main.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT","true");
        props.put("remote.connection.main.connect.options.org.xnio.Options.SSL_ENABLED", "false");
        props.put("remote.connection.main.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
        return props;
    }

}
