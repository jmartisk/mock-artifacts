package experiment;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
/*@ConnectionDefinition(connectionFactory = DummyConnectionFactory.class,
        connectionFactoryImpl = DummyConnectionFactoryImpl.class,
        connection = DummyConnection.class,
        connectionImpl = DummyConnectionImpl.class)*/
public class DummyManagedConnectionFactory implements ManagedConnectionFactory, Serializable {

    DummyConnectionFactory factory;

    Map<ConnectionRequestInfo, DummyManagedConnection> managedConnectionsMap = new HashMap<>();

    private PrintWriter logWriter;

    private static Logger log = Logger.getLogger("DummyManagedConnectionFactory");

    public DummyManagedConnectionFactory() {

    }

    @Override
    public Object createConnectionFactory(ConnectionManager connectionManager) throws ResourceException {
        log.debug("DummyManagedConnectionFactory.createConnectionFactory, connectionManager = [" + connectionManager + "]");
        if (factory == null) {
            factory = new DummyConnectionFactoryImpl(this, connectionManager);
        }
        return factory;
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        throw new ResourceException("This resource adapter doesn't support non-managed environments");
    }

    @Override
    public ManagedConnection createManagedConnection(Subject subject,
                                                     ConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {
        log.debug("DummyManagedConnectionFactory.createManagedConnection");
        log.debug("subject = [" + subject + "], connectionRequestInfo = [" + connectionRequestInfo + "]");
        final DummyManagedConnection managedConnection = new DummyManagedConnection(this);
        managedConnectionsMap.put(connectionRequestInfo, managedConnection);
        return managedConnection;
    }

    @Override
    public ManagedConnection matchManagedConnections(Set set, Subject subject,
                                                     ConnectionRequestInfo connectionRequestInfo)
            throws ResourceException {
        ManagedConnection result = null;
        Iterator it = set.iterator();
        while (result == null && it.hasNext()) {
            ManagedConnection mc = (ManagedConnection) it.next();
            if (mc instanceof DummyManagedConnection) {
                result = mc;
            }
        }
        return result;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws ResourceException {
        logWriter = printWriter;

    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return logWriter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DummyManagedConnectionFactory that = (DummyManagedConnectionFactory)o;

        if (factory != null ? !factory.equals(that.factory) : that.factory != null) {
            return false;
        }
        return managedConnectionsMap != null ? managedConnectionsMap.equals(that.managedConnectionsMap)
                : that.managedConnectionsMap == null;

    }

    @Override
    public int hashCode() {
        int result = factory != null ? factory.hashCode() : 0;
        result = 31 * result + (managedConnectionsMap != null ? managedConnectionsMap.hashCode() : 0);
        return result;
    }
}
