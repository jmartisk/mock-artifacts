package ejb;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

@Stateful
@SecurityDomain("other")
public class HelloBean implements HelloBeanRemote {

    @Resource
    SessionContext ctx;

    private Long counter;

    public HelloBean() {
    }

    @PostConstruct
    public void init() {
        counter = 0L;
    }

    @Override
    @RolesAllowed("users")
    public String hello() {
        final String node = System.getProperty("jboss.node.name");
        final String message = "method hello() invoked by user " + ctx.getCallerPrincipal().getName()
                + " on node " + node + ", counter = " + ++counter;
        System.out.println(message);
        return message;
    }

}
