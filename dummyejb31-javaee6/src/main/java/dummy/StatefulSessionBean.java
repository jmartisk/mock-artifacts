package dummy;

import javax.ejb.Stateful;
import java.io.Serializable;

/**
 * Author: jmartisk
 * Date: 6/28/12
 * Time: 2:51 PM
 */
@Stateful
public class StatefulSessionBean implements Serializable {

    public String sayHello(String name) {
        return "Buenos días " + name + "!";
    }

}
