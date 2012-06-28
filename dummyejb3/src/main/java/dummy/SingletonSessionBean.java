package dummy;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 * Author: jmartisk
 * Date: 6/28/12
 * Time: 2:51 PM
 */
@Singleton
public class SingletonSessionBean {

    @PostConstruct
    public void postConstruct() {

    }
}
