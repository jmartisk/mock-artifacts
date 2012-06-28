package dummy;

import javax.ejb.Stateless;

/**
 * Author: jmartisk
 * Date: 6/28/12
 * Time: 2:51 PM
 */
@Stateless
public class StatelessSessionBean {

    public String sayHello(String name) {
        return "Hyvää päivää " + name + "!";
    }

}
