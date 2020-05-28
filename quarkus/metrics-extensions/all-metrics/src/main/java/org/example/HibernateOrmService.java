package org.example;

import org.example.persistence.DummyEntity;

import javax.inject.Inject;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

@Path("/hibernate")
public class HibernateOrmService {

    private static Logger logger = Logger.getLogger(HibernateOrmService.class.getName());

    @Inject
    UserTransaction tx;

    @GET
    @Path("/")
    public void doStuffWithEntities() throws SystemException, NotSupportedException {
        int count = ThreadLocalRandom.current().nextInt(50)+1;
        tx.begin();
        try {
            logger.info("Persisting " + count + " entities");
            for (int i = 0; i < count; i++) {
                DummyEntity dummyEntity = new DummyEntity();
                dummyEntity.persist();
                if(ThreadLocalRandom.current().nextBoolean()) {
                    dummyEntity.setNumber(1L);
                }
                if(ThreadLocalRandom.current().nextBoolean()) {
                    dummyEntity.delete();
                }
            }
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        }
    }

}
