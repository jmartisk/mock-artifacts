package experiment;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
public class DummyManagedConnection implements ManagedConnection {

    private final ManagedConnectionFactory mcf;

    private static Logger logger = Logger.getLogger(DummyManagedConnection.class.getName());

    private PrintWriter printWriter;

    private List<DummyConnectionImpl> connections = new ArrayList<>();

    private List<ConnectionEventListener> connectionEventListeners = new CopyOnWriteArrayList<>();

    public DummyManagedConnection(ManagedConnectionFactory mcf) {
        logger.debug("Creating new managed connection: " + this.toString());
        this.mcf = mcf;
    }

    @Override
    public Object getConnection(Subject subject, ConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {
        DummyConnectionImpl physicalConnection = new DummyConnectionImpl(this, mcf);
        logger.debug("Managed connection " + this.toString()+ " returning connection handle: " + physicalConnection.toString());
        connections.add(physicalConnection);
        return physicalConnection;
    }

    @Override
    public void destroy() throws ResourceException {
        logger.debug("Destroyed managed connection: " + this.toString());
    }

    @Override
    public void cleanup() throws ResourceException {
        logger.debug("Cleanup of managed connection: " + this.toString());
        for (DummyConnectionImpl connection : connections) {
            connection.setManagedConnection(null);
        }
        connections.clear();
    }

    @Override
    public void associateConnection(Object connection) throws ResourceException {
        if (connection == null) { throw new ResourceException("Null connection handle"); }
        if (!(connection instanceof DummyConnectionImpl)) { throw new ResourceException("Wrong connection handle"); }
        logger.debug("Managed connection " + this.toString() + " being associated to connection " + connection.toString());
        connections.add((DummyConnectionImpl)connection);
    }

    @Override
    public void addConnectionEventListener(ConnectionEventListener connectionEventListener) {
        logger.info("DummyManagedConnection.addConnectionEventListener: " + connectionEventListener.toString());
        connectionEventListeners.add(connectionEventListener);
    }

    @Override
    public void removeConnectionEventListener(ConnectionEventListener connectionEventListener) {
        connectionEventListeners.remove(connectionEventListener);
    }

    @Override
    public XAResource getXAResource() throws ResourceException {
        return null;
    }

    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        return null;
    }

    @Override
    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws ResourceException {
        this.printWriter = printWriter;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return printWriter;
    }

    public void closeHandle(DummyConnectionImpl handle) {
        connections.remove(handle);
        ConnectionEvent event = new ConnectionEvent(this, ConnectionEvent.CONNECTION_CLOSED);
        event.setConnectionHandle(handle);
        logger.info("Listeners size: " + connectionEventListeners.size());
        logger.info("Listeners: " + connectionEventListeners);
        for (ConnectionEventListener cel : connectionEventListeners)
        {
            cel.connectionClosed(event);
        }
    }
}
