package batchexample.job;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemReader;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import batchexample.exceptions.PersonReaderException;
import batchexample.model.Person;
import batchexample.model.PersonCheckpoint;

/**
 * A reader which reads persons' names from a text file named /people/people-list located in the deployment
 *
 * @author Jan Martiska
 */
@Named
public class PersonReader implements ItemReader {

    @Inject
    Logger logger;

    private BufferedReader reader;

    private Long itemsRead = 0L;

    @Inject
    @BatchProperty(name = "reader.fail.at")
    private String failAt;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        final InputStream inputStream = this.getClass().getResourceAsStream("/people/people-list");
        reader = new BufferedReader(new InputStreamReader(inputStream));
        logger.info("PersonReader opened file for reading, checkpoint=" + checkpoint);
        if (checkpoint != null) {
            // if we are resuming from a checkpoint, skip the appropriate number of entries
            if (checkpoint instanceof PersonCheckpoint) {
                final PersonCheckpoint personCheckpoint = (PersonCheckpoint)checkpoint;
                final long personsCompleted = personCheckpoint.getPersonsCompleted();
                logger.info("PersonReader skipping first " + personsCompleted + " persons");
                for (int i = 0; i < personsCompleted; i++) {
                    final String name = reader.readLine();
                    if (name == null) {
                        throw new Error("Was supposed to skip " + personsCompleted
                                + " persons, but there seem to be not enough listed in the file");
                    }
                }
                itemsRead = personsCompleted;
            } else {
                throw new Error("PersonReader.open(checkpoint) expects a PersonCheckpoint instance, but was: "
                        + checkpoint.getClass());
            }
        }
    }

    @Override
    public void close() throws Exception {
        logger.info(this.toString() + " closing");
        reader.close();
    }

    @Override
    public Object readItem() throws Exception {
        if (failAt != null && itemsRead.equals(Long.parseLong(failAt))) {
            logger.info("PersonReader failing the job after reading " + itemsRead + " items");
            throw new PersonReaderException();
        }
        String name = reader.readLine();
        if (name != null) {
            name = name.trim();
            logger.info("Found person: " + name);
            final Person person = new Person();
            person.setName(name);
            itemsRead++;
            return person;
        } else {
            logger.info("Reader reached end of file");
            return null;
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        PersonCheckpoint checkpoint = new PersonCheckpoint(itemsRead);
        logger.info("PersonReader asked for checkpointInfo, returning " + checkpoint);
        return checkpoint;
    }

}
