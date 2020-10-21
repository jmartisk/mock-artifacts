package org.example;

import org.eclipse.microprofile.opentracing.Traced;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@Traced
public class Service {

    Logger logger = Logger.getLogger("INNER");

    public void inner() {
        logger.info("inner: " + MDC.get("parentId"));
    }
}
