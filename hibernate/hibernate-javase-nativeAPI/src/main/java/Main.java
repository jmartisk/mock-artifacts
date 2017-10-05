import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * @author Jan Martiska
 */
public class Main {

    public static void main(String[] args) {
        try(SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(StupidEntity.class)
                .configure().buildSessionFactory()) {
			try (Session session = sessionFactory.openSession()) {
				Transaction tx = session.getTransaction();
				tx.begin();
				StupidEntity entity = new StupidEntity();
				session.persist(entity);
				tx.commit();
				System.out.println(entity.getId());
			}
		}
    }
}
