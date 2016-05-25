package dummy;

import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
public class DummyResourceAdapter implements ResourceAdapter {

    private static Logger logger = Logger.getLogger(DummyResourceAdapter.class.getName());

    @Override
    public void start(BootstrapContext bootstrapContext) throws ResourceAdapterInternalException {
        logger.info(this.getClass().getName() + " starting");
    }

    @Override
    public void stop() {
        logger.info(this.getClass().getName() + " stopping");

    }

    @Override
    public void endpointActivation(MessageEndpointFactory messageEndpointFactory,
                                   ActivationSpec activationSpec) throws ResourceException {
        // TODO method DummyResourceAdapter.endpointActivation
        throw new UnsupportedOperationException(
                "DummyResourceAdapter.endpointActivation not implemented yet");

    }

    @Override
    public void endpointDeactivation(MessageEndpointFactory messageEndpointFactory,
                                     ActivationSpec activationSpec) {
        // TODO method DummyResourceAdapter.endpointDeactivation
        throw new UnsupportedOperationException(
                "DummyResourceAdapter.endpointDeactivation not implemented yet");

    }

    @Override
    public XAResource[] getXAResources(ActivationSpec[] activationSpecs) throws ResourceException {
        // TODO method DummyResourceAdapter.getXAResources
        throw new UnsupportedOperationException("DummyResourceAdapter.getXAResources not implemented yet");

    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


}
