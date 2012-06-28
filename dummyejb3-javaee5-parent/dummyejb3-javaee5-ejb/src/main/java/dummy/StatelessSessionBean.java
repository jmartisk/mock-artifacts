package dummy;

import dummy.iface.StatelessIface;

import javax.ejb.Local;
import javax.ejb.Stateless;

/**
 * Author: jmartisk
 * Date: 6/28/12
 * Time: 2:51 PM
 */
@Stateless(mappedName = "stateless")
@Local(StatelessIface.class)
public class StatelessSessionBean implements StatelessIface {

    public String sayHello(String name) {
        return "Hyvää päivää " + name + "!";
    }

}
