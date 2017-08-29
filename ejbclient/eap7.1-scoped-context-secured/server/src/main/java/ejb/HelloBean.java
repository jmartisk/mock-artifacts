package ejb;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

@Stateless
@SecurityDomain("other")
public class HelloBean implements HelloBeanRemote {

    @Resource
    SessionContext ctx;

    public HelloBean() {
    }

    @Override
    @RolesAllowed("users")
    public String hello() {
        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ctx.getCallerPrincipal().getName();
    }

}
