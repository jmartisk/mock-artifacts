package ejb;

import javax.ejb.Stateless;

@Stateless
public class HelloBean implements HelloBeanRemote {

    public HelloBean() {
    }

    @Override
    public String hello(String name) {
        System.out.println("invocation of hello("+name+")");
        return "Hello, " + name;
    }

}
