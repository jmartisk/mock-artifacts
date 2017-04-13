package batchexample.helpers;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

/**
 * @author Jan Martiska
 */
public class LogProducer {

    @Produces
    Logger getLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getSimpleName());
    }
}
