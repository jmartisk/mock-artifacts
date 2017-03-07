package client;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.runner.RunWith;

import ejb.HelloBeanRemote;

/**
 * @author Jan Martiska
 */
@RunWith(BMUnitRunner.class)
public class ClientTest {

    @org.junit.Test
    @BMRules(rules = {
            @BMRule(name="initialize countdown",
                    targetClass = "EJBInvocationHandler",
                    targetMethod = "<init>",
                    action = "createCountDown(\"cd\", 3); "
                            + "System.out.println(\"initializing countdown..\");"),
            @BMRule(name = "throw RuntimeException",
                    targetClass = "MethodInvocationMessageWriter",
                    targetMethod = "writeMessage",
                    condition = "countDown(\"cd\")",
                    action = "createCountDown(\"cd\", 5);"
                            + "System.out.println(\"[byteman] INJECTING IOException to simulate networking problem\");"
                            + "throw new java.io.IOException(\"BYTEMAN-INJECTED EXCEPTION\"); "
            )
    })
    public void doTest() throws Exception {
        InitialContext ctx = new InitialContext(getCtxProperties());
        String lookupName = "ejb:/server/HelloBean!ejb.HelloBeanRemote";
        HelloBeanRemote bean = (HelloBeanRemote)ctx.lookup(lookupName);
        for (int i = 0; i < 8; i++) {
            try {
                System.out.println(bean.hello("Joe"));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        ctx.close();
    }

    public Properties getCtxProperties() {
        Properties props = new Properties();
        props.put("org.jboss.ejb.client.scoped.context", true);
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        props.put("remote.connections", "main");
        props.put("remote.connection.main.host", "localhost");
        props.put("remote.connection.main.port", "4447");
        props.put("remote.connection.main.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS","false");
        props.put("remote.connection.main.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT","true");
        props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        return props;
    }
}
