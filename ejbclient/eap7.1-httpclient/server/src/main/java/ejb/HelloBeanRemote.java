package ejb;

import javax.ejb.Remote;

@Remote
public interface HelloBeanRemote {

    String whoami();

}


