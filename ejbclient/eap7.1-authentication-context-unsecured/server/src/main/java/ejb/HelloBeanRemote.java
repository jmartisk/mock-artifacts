package ejb;

import javax.ejb.Remote;

/**
 * @author jmartisk
 */
@Remote
public interface HelloBeanRemote {

    public String hello();

}


