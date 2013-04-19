package dummy;


import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
@EJB(name = "java:global/MyBean", beanInterface = BeanWithName.class)
public class BeanWithName {

    public void doNothing() {

    }

}
