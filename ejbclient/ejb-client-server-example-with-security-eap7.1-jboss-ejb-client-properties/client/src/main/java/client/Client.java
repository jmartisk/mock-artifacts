package client;

import java.security.PrivilegedActionException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.HelloBeanRemote;

/**
 * @author jmartisk
 */
public class Client {

    public static void main(String[] args) throws NamingException, PrivilegedActionException {
            try {
                InitialContext ctx = new InitialContext(getCtxProperties());
                String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
                HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
                System.out.println(bean.hello());
                ctx.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
    }

    public static Properties getCtxProperties() {
        Properties props = new Properties();
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        return props;
    }

}
