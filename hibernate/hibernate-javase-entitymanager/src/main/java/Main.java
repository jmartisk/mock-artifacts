import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metamodel.source.internal.SessionFactoryBuilderImpl;
import org.hibernate.type.StandardBasicTypes;

/**
 * @author Jan Martiska
 */
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory pu = Persistence.createEntityManagerFactory("MainPU");
        EntityManager em = pu.createEntityManager();
        em.getTransaction().begin();

        StupidEntity stupidEntity = new StupidEntity();
        em.persist(stupidEntity);
        em.getTransaction().commit();

    }
}
