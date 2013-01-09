package playground;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * @author jmartisk
 * @since 12/13/12
 */
@Singleton
@Startup
public class EventSource {

    @Inject
    Event<SuperEvent> event;

    public void fireEvent() {
        event.fire(new SuperEvent());
    }

}
