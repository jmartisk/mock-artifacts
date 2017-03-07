import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PreRemove;

/**
 * @author Jan Martiska
 */
@Stateless
@LocalBean
@Named(value = "EJBBatchlet")
public class PrintInjectedPropertyBatchlet implements Batchlet {

    @Inject
    @BatchProperty(name = "injected.property")
    private String property;

    @Override
    public String process() throws Exception {
        System.out.println("Injected property value = " + property + ", into object " + this.toString());
        return "ok";
    }

    @Override
    public void stop() throws Exception {

    }
}
