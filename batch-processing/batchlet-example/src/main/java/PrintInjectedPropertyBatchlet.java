import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Jan Martiska
 */
@Named(value = "printInjectedPropertyBatchlet")
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
