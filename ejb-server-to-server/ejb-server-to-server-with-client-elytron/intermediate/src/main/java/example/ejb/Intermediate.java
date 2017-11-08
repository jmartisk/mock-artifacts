package example.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author <a href="mailto:mjurc@redhat.com">Michal Jurc</a>
 */
@Stateless
public class Intermediate implements WhoAmIBeanRemote {

    @EJB(lookup = "ejb:/server-side/WhoAmIBean!example.ejb.WhoAmIBeanRemote")
    private WhoAmIBeanRemote serverSideWhoAmI;

    public String whoAmI() {
        try {
            return serverSideWhoAmI.whoAmI();
        } catch(Exception e) {
            throw new RuntimeException("Intermediary server was unable to call the target server");
        }
    }

}
