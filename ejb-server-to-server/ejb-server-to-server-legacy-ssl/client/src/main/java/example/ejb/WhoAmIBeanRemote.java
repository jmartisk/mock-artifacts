package example.ejb;

import javax.ejb.Remote;

/**
 * @author Jan Martiska
 */
@Remote
public interface WhoAmIBeanRemote {

    String whoAmI();
}
