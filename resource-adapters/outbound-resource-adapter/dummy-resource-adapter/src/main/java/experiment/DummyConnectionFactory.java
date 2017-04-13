package experiment;

import javax.resource.ResourceException;

/**
 * @author Jan Martiska
 */
public interface DummyConnectionFactory  {

    DummyConnectionImpl getConnection() throws ResourceException;

}
