package ejb;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.ejb3.annotation.SecurityDomain;

import entity.DummyEntity;

@Stateless
@SecurityDomain("other")
public class TransactionalBean implements TransactionalBeanRemote {

    @Resource
    private SessionContext ctx;


    @PersistenceContext
    private EntityManager em;

    public TransactionalBean() {
    }

    @Override
    @RolesAllowed("users")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void createEntity() {
        System.out.println("method createEntity() invoked by user " + ctx.getCallerPrincipal().getName());
        em.persist(new DummyEntity());
    }

    @Override
    @RolesAllowed("users")
    public long getEntitiesCount() {
        return em.createQuery("from DummyEntity d", DummyEntity.class).getResultList().size();
    }

}
