package ejb;

import javax.ejb.Remote;

/**
 * @author jmartisk
 */
@Remote
public interface HelloBeanRemote {

    String hello();

}


