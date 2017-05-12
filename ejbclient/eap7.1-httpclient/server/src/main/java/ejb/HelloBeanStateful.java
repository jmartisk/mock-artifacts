package ejb;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.SecurityDomain;

@Stateful
@SecurityDomain("other")
public class HelloBeanStateful implements HelloBeanRemote {

    @Resource
    SessionContext ctx;

    public HelloBeanStateful() {
    }

    @Override
    @RolesAllowed("users")
    public String whoami() {
        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName());
        return ctx.getCallerPrincipal().getName() + " (stateful)";
    }

}
