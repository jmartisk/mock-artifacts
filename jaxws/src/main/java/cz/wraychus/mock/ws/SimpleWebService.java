package cz.wraychus.mock.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

/**
 * @author jmartisk
 * @since 1/9/13
 */
@WebService
@Addressing(enabled = true)
public class SimpleWebService {

    @WebMethod
    public void throwFault() throws Exception {
        throw new Exception("This is a fatal brutal error.");
    }

    @WebMethod
    public String sayHello(@WebParam(name = "MyName") String name) {
        return "Hello " + name;
    }

}
