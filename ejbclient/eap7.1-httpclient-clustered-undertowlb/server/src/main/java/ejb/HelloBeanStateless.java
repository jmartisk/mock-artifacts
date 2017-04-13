package ejb;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

@Stateless
@SecurityDomain("other")
public class HelloBeanStateless implements HelloBeanRemote {

    @Resource
    SessionContext ctx;


    public HelloBeanStateless() {
    }

    @Override
    @PermitAll
    public String hello() {
        String nodeName = System.getProperty("jboss.node.name");
        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName());
        return ctx.getCallerPrincipal().getName() + " @ " + nodeName;
    }

}
