package example;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author Jan Martiska
 */
@Singleton
@Startup
public class Runner {

    @PostConstruct
    public void runExample() {
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(StupidEntity.class)
                .configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        session.persist(new StupidEntity());
        tx.commit();
    }
    
}
