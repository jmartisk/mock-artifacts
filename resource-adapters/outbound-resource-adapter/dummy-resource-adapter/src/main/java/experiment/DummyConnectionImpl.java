package experiment;

import javax.resource.spi.ManagedConnectionFactory;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
public class DummyConnectionImpl implements DummyConnection {

    private volatile boolean valid = true;
    private volatile boolean closed = false;

    private static Logger logger = Logger.getLogger(DummyConnectionImpl.class.getName());

    private DummyManagedConnection managedConnection;

    public DummyConnectionImpl(
            DummyManagedConnection dummyManagedConnection, ManagedConnectionFactory mcf) {
        logger.debug("Creating: "  + this.toString());
        this.managedConnection = dummyManagedConnection;
    }

    public void setManagedConnection(DummyManagedConnection connection) {
        this.managedConnection = connection;
    }

    @Override
    public void close() {
        closed = true;
        logger.debug("Closing connection handle: " + this.toString());
        if (managedConnection != null)
        {
            managedConnection.closeHandle(this);
            managedConnection = null;
        }
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
