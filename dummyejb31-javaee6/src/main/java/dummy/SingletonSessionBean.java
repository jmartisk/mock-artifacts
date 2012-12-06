package dummy;

import dummy.aux.ShouldBeIntercepted;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Author: jmartisk
 * Date: 6/28/12
 * Time: 2:51 PM
 */
@Singleton
public class SingletonSessionBean implements Serializable {

    @Inject
    private Logger logger;

    @PostConstruct
    public void postConstruct() {
        logger.info("Hello, I am Singleton bean's PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        logger.info("Hello, I am Singleton bean's PreDestroy");
    }

    @ShouldBeIntercepted
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }

}
