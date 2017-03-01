import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Jan Martiska
 */
@Stateless
@LocalBean
@Named(value = "EJBBatchlet")
public class EJBBatchlet implements Batchlet {

    @Inject
    @BatchProperty(name = "injected.property")
    private String property;

    @Override
    public String process() throws Exception {
        System.out.println("Injected property value = " + property);
        return "ok";
    }

    @Override
    public void stop() throws Exception {

    }
}
