package cz.wraychus.dummy.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author jmartisk
 */
@ManagedBean
@RequestScoped
public class MyAwesomeManagedBean {

    public String saySomethingAwesome() {
        return "When you are right, no one remembers. When you're wrong, no one forgets.";
    }

}
