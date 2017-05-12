import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.j2ee.Management;
import javax.management.j2ee.ManagementHome;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author Jan Martiska
 */
@Singleton
@Startup
public class JSR77Tester {



    @PostConstruct
    public void initialize(){
        try {
            Context ic = new InitialContext();
            Object obj = ic.lookup("ejb/mgmt/MEJB");
            ManagementHome mejbHome = (ManagementHome)obj;

            final Management management = mejbHome.create();
            System.out.println("default domain: " + management.getDefaultDomain());
            System.out.println("MBean count: " + management.getMBeanCount());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

}
