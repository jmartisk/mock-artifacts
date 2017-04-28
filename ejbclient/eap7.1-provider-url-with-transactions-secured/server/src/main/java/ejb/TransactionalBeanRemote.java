package ejb;

import javax.ejb.Remote;

/**
 * @author jmartisk
 */
@Remote
public interface TransactionalBeanRemote {

    void createEntity();

    long getEntitiesCount();

}


