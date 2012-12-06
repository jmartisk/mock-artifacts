package cz.wraychus.mock.persistence;

import cz.wraychus.mock.persistence.entity.User;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author jmartisk
 * @since 12/6/12
 */
@Stateless
public class ManagerEjb {

    @PersistenceContext(unitName = "AwesomePersistenceUnit")
    private EntityManager em;

    @Resource
    SessionContext ctx;


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void createUser(String username, int numberOfEyes) {
        User user = new User();
        user.setUsername(username);
        user.setNumberOfEyes(numberOfEyes);
        em.persist(user);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public User getUserByUsername(String username) {
        return em.find(User.class, username);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void rollbackATransaction() {
        ctx.setRollbackOnly();
        throw new HorribleException();
    }

}
