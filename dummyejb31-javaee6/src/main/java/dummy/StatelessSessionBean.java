package dummy;

import dummy.aux.NamedAfterClass;
import dummy.aux.ShouldBeIntercepted;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Author: jmartisk
 * Date: 6/28/12
 * Time: 2:51 PM
 */
@Stateless
public class StatelessSessionBean {

    @Inject
    @NamedAfterClass
    private Logger logger;

    @PostConstruct
    public void postConstruct() {
        logger.info("Hello, I am Stateless bean's PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        logger.info("Hello, I am Stateless bean's PreDestroy");
    }

    @ShouldBeIntercepted
    public String sayHello(String name) {
        return "Guten Tag " + name + "!";
    }




}
