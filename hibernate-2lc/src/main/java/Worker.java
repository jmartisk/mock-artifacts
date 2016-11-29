import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

/**
 * @author Jan Martiska
 */
@Singleton
@Startup
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class Worker {

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction tx;

    @PostConstruct
    public void doSomething() {
        try {
            final Cache cache = em.getEntityManagerFactory().getCache();

            tx.begin(); // we need this because the cache is transactional
            DumbEntity alice = new DumbEntity("alice");
            DumbEntity bob = new DumbEntity("bob");
            em.persist(alice);
            em.persist(bob);
            tx.commit();

            em.find(DumbEntity.class, "alice"); // cache Alice

            System.out.println("Contains Alice? " + cache.contains(DumbEntity.class, "alice"));
            System.out.println("Contains Bob? " + cache.contains(DumbEntity.class, "bob"));
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
