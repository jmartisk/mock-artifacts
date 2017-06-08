package example.ejb;

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
public class WhoAmIBean implements WhoAmIBeanRemote {

    @Resource
    private SessionContext ctx;

    @Override
    @RolesAllowed("users")
    public String whoAmI() {
        final String user = ctx.getCallerPrincipal().getName();
        System.out.println("WhoAmIBean.whoAmI called as " + user);
        return user;
    }
}
