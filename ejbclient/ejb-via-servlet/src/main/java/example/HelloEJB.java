package example;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

/**
 * @author Jan Martiska
 */
@Stateless
@SecurityDomain("other")
public class HelloEJB {

    @Resource
    private SessionContext ctx;

    @RolesAllowed("users")
    public String getUsername() {
        return ctx.getCallerPrincipal().getName();
    }
}
