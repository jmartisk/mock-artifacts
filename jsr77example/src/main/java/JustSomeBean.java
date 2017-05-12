import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;

/**
 * @author Jan Martiska
 */
@Singleton
@Startup
public class JustSomeBean {

    @PostConstruct
    public void initialize() {
        System.out.println("WORKS!");

    }

}
