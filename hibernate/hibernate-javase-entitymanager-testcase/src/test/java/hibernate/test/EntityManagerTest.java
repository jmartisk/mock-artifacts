package hibernate.test;

import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.jboss.logging.Logger;


public class EntityManagerTest extends TestCase {
	
	private EntityManager em;

	private static Logger logger = Logger.getLogger(EntityManagerTest.class.getName());
	
	@SuppressWarnings("deprecation")
	@Override
	protected void setUp() throws Exception {
		logger.info("***** HIBERNATE VERSION: " + org.hibernate.Version.getVersionString());
		em = Persistence.createEntityManagerFactory("default").createEntityManager();
	}
	
	@Override
	protected void tearDown() throws Exception {
		if (em != null) {
			em.close();
		}
	}
	
	public void testHHH6326() {
		EntityTransaction ets = em.getTransaction();
		
		ets.begin();
        String hql = "select e.lastName, e.department.deptName, e.title from Employee e inner join e.department inner join e.title";
        
        Query qry = em.createQuery(hql);
        qry.setMaxResults(10);
        qry.getResultList();
		ets.commit();
	}

}
