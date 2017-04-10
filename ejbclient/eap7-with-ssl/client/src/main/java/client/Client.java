package client;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.HelloBeanRemote;

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
        props.put("remote.connection.main.host", "127.0.0.1");
        props.put("remote.connection.main.port", "8443");
        props.put("remote.connection.main.protocol", "https-remoting");
        props.put("remote.connection.main.username", "joe");
        props.put("remote.connection.main.password", "joeIsAwesome2013!");
        props.put("remote.connection.main.connect.options.org.xnio.Options.SSL_ENABLED", "true");
        props.put("remote.connection.main.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
        return props;
    }

}
