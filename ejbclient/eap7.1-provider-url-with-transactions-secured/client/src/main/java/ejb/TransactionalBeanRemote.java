package ejb;

/**
 * @author jmartisk
 */
public interface TransactionalBeanRemote {

    void createEntity();

    long getEntitiesCount();

}


