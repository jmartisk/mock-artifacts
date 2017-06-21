package client;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.runner.RunWith;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

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
                    targetClass = "EJBInvocationHandler",
                    targetMethod = "invoke",
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
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        props.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080");
        return props;
    }
}
