package batchexample.job;

import java.io.Serializable;
import java.util.List;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemWriter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import batchexample.exceptions.PersonWriterException;
import batchexample.model.PersonCheckpoint;

/**
 * A writer for persons which saves the persons into a persistence context.
 *
 * @author Jan Martiska
 */
@Named
public class PersonWriter implements ItemWriter {

    @PersistenceContext
    private EntityManager em;

    @Inject
    Logger logger;

    private Long itemsWritten = 0L;

    @Inject
    @BatchProperty(name = "writer.fail.at")
    private String failAt;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        logger.info("Writer opened from checkpoint=" + checkpoint);
        if (checkpoint != null) {
            // if we are resuming from a checkpoint, skip the appropriate number of entries
            if (checkpoint instanceof PersonCheckpoint) {
                final PersonCheckpoint personCheckpoint = (PersonCheckpoint)checkpoint;
                itemsWritten = personCheckpoint.getPersonsCompleted();
            } else {
                throw new Error("PersonWriter.open(checkpoint) expects a PersonCheckpoint instance, but was: "
                        + checkpoint.getClass());
            }
        }
    }

    @Override
    public void close() throws Exception {
        logger.info(this.toString() + " closing");
    }

    @Override
    public void writeItems(List<Object> list) throws Exception {
        if (failAt != null && itemsWritten.equals(Long.parseLong(failAt))) {
            throw new PersonWriterException();
        }
        list.forEach(person -> {
            em.persist(person);
            itemsWritten++;
            logger.info("Persisted: " + person);
        });
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        PersonCheckpoint checkpoint = new PersonCheckpoint(itemsWritten);
        logger.info("PersonWriter asked for checkpointInfo, returning " + checkpoint);
        return checkpoint;
    }
}
