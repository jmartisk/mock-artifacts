package batchexample.job;

import java.util.UUID;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import batchexample.exceptions.PersonProcessorException;
import batchexample.model.Person;

/**
 * A processor for Persons which generates a random password for each processed person.
 *
 * @author Jan Martiska
 */
@Named
public class PersonProcessor implements ItemProcessor {

    @Inject
    Logger logger;

    @Inject
    @BatchProperty(name = "processor.fail.at")
    private Long failAt;

    @Inject
    @BatchProperty(name = "processor.crash.at")
    private Long crashAt;

    private Long itemsProcessed = 0L;

    @Override
    public Object processItem(Object o) throws Exception {
        if (failAt != null && itemsProcessed.equals(failAt)) {
            logger.info("Processor failing after " + failAt + " items");
            throw new PersonProcessorException();
        }
        if (crashAt != null && itemsProcessed.equals(crashAt)) {
            logger.info("Processor crashing the server after " + crashAt + " items. See you in hell!");
            Runtime.getRuntime().halt(76);   // a very random exit code
        }
        try {
            Person person = (Person)o;
            person.setPassword(UUID.randomUUID().toString());
            logger.info("Processed " + o);
            itemsProcessed++;
            return o;
        } catch (ClassCastException cce) {
            throw new IllegalArgumentException(
                    "PersonProcessor can only process persons, but was given a: " + o.getClass());
        }
    }

}
