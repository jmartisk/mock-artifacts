import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metamodel.source.internal.SessionFactoryBuilderImpl;

/**
 * @author Jan Martiska
 */
public class Main {

    public static void main(String[] args) {
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
