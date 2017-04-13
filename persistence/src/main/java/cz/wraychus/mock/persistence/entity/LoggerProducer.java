package cz.wraychus.mock.persistence.entity;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * @author jmartisk
 */
public class LoggerProducer {

    @Produces
    @NamedAfterClass
    @Dependent
    Logger getLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
