package org.example;

import org.hibernate.Session;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.stat.Statistics;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ThreadLocalRandom;

@Path("/")
public class MyResource {

    @Inject
    EntityManager em;

    @Inject
    UserTransaction tx;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/create")
//    @Transactional
    public String create() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        Long id;
        tx.begin();
        try {
            Gift gift = new Gift();
            gift.setId(ThreadLocalRandom.current().nextLong(1000000000));
            em.persist(gift);
            tx.commit();
            id = gift.getId();
        }
        catch (Throwable t) {
            tx.rollback();
            throw t;
        }

        tx.begin();
        try {
            // in another tx, do some more shenanigans to update more metrics
            Gift gift1 = em.find(Gift.class, id);
            gift1.setDescription("Awesome!");
            em.merge(gift1);
            em.flush();
            em.createQuery("from Gift f").getResultList();
            tx.commit();
        }
        catch (Throwable t) {
            tx.rollback();
        }

        // session statistics
        // quite useless it seems
        final SessionStatistics statistics = em.unwrap(Session.class).getStatistics();
        System.out.println(statistics);

        // session factory statistics
        // much better
        final Statistics puStatistics = em.unwrap(Session.class).getSessionFactory().getStatistics();
        System.out.println(puStatistics);
        puStatistics.getTransactionCount();


        return "OK, created Gift with id = " + id;
    }
}