import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Jan Martiska
 */
public class Main {

    public static void main(String[] args) {
        EntityManagerFactory pu = Persistence.createEntityManagerFactory("MainPU");
        EntityManager em = pu.createEntityManager();
        try {
            em.getTransaction().begin();
            StupidEntity stupidEntity = new StupidEntity();
            em.persist(stupidEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
            pu.close();
        }

    }
}
