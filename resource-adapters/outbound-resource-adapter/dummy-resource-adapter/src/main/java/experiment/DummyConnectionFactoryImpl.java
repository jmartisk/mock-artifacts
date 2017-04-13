package experiment;

import java.io.Serializable;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.Referenceable;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
public class DummyConnectionFactoryImpl implements DummyConnectionFactory, Serializable, Referenceable {

    private Reference reference;

    private DummyManagedConnectionFactory managedConnectionFactory;

    private ConnectionManager connectionManager;

    private static Logger logger = Logger.getLogger(DummyConnectionFactoryImpl.class.getName());

    public DummyConnectionFactoryImpl(DummyManagedConnectionFactory dummyManagedConnectionFactory,
                                      ConnectionManager connectionManager) {
        logger.debug("DummyConnectionFactoryImpl.DummyConnectionFactoryImpl constructor");
        logger.debug("dummyManagedConnectionFactory = [" + dummyManagedConnectionFactory
                + "], connectionManager = [" + connectionManager + "]");
        this.managedConnectionFactory = dummyManagedConnectionFactory;
        this.connectionManager = connectionManager;
    }

    @Override
    public DummyConnectionImpl getConnection() throws ResourceException {
        logger.debug("DummyConnectionFactoryImpl.getConnection");
        return (DummyConnectionImpl)connectionManager.allocateConnection(managedConnectionFactory, null);
    }

    @Override
    public void setReference(Reference reference) {
        this.reference = reference;
    }

    @Override
    public Reference getReference() throws NamingException {
        return reference;
    }

}
