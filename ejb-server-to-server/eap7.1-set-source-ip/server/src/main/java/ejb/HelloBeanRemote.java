package ejb;

import javax.ejb.Remote;

/**
 * @author jmartisk
 * @since 7/3/13
 */
@Remote
public interface HelloBeanRemote {

    public String hello();

}


