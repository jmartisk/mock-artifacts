package playground;

import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;

/**
 * @author jmartisk
 */
@Singleton
@Startup
@DependsOn("EventSource")
public class EventConsumer {

    public boolean received = false;

    public void woohoo(@Observes SuperEvent event){
        received = true;
    }

}
