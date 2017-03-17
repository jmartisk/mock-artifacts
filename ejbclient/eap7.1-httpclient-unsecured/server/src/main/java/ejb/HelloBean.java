package ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 * @author jmartisk
 * @since 7/3/13
 */
@Stateless
public class HelloBean implements HelloBeanRemote {

    @Resource
    SessionContext ctx;

    public HelloBean() {
    }

    @Override
    public String hello() {
        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName());
        return ctx.getCallerPrincipal().getName();
    }

}
