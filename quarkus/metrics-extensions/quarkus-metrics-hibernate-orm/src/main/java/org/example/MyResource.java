package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.stat.Statistics;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ThreadLocalRandom;

@Path("/")
public class MyResource {

    @Inject
    EntityManager em;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/create")
    @Transactional
    public Long create() {
        Gift gift = new Gift();
        gift.setId(ThreadLocalRandom.current().nextLong(1000000000));
        em.persist(gift);


        // session statistics
        // quite useless it seems
        final SessionStatistics statistics = em.unwrap(Session.class).getStatistics();
        System.out.println(statistics);
        System.out.println("statistics.getCollectionCount() = " + statistics.getCollectionCount());
        System.out.println("statistics.getCollectionKeys() = " + statistics.getCollectionKeys());
        System.out.println("statistics.getEntityCount() = " + statistics.getEntityCount());
        System.out.println("statistics.getEntityKeys() = " + statistics.getEntityKeys());

        // session factory statistics
        // much better
        final Statistics puStatistics = em.unwrap(Session.class).getSessionFactory().getStatistics();
        System.out.println(puStatistics.getClass());
        System.out.println(puStatistics);
        puStatistics.getTransactionCount();


        return gift.getId();
    }
}