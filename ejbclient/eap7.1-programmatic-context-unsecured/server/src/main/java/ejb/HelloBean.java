package ejb;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

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
        System.out.println("method hello() invoked");
        return "ok";
    }

}
