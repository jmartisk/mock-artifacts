package ejb;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

//@Stateful
@Stateless
@SecurityDomain("other")
public class HelloBean implements HelloBeanRemote {

    @Resource
    SessionContext ctx;

//    private Long counter;

    public HelloBean() {
    }

//    @PostConstruct
//    public void init() {
//        counter = 0L;
//    }

    @Override
    @PermitAll
    public String hello() {
        String nodeName = System.getProperty("jboss.node.name");
//        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName() + ", counter = " + ++counter);
        System.out.println("method hello() invoked by user " + ctx.getCallerPrincipal().getName());
        return ctx.getCallerPrincipal().getName() + " @ " + nodeName;
    }

}
