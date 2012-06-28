package dummy;

import dummy.iface.StatefulIface;

import javax.ejb.Local;
import javax.ejb.Stateful;
import java.io.Serializable;

/**
 * Author: jmartisk
 * Date: 6/28/12
 * Time: 2:51 PM
 */
@Stateful(mappedName = "stateful")
@Local(StatefulIface.class)
public class StatefulSessionBean implements Serializable, StatefulIface {

    public String sayHello(String name) {
        return "Buenos días " + name + "!";
    }

}
