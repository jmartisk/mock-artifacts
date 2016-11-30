package hibernatereproducer;

import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.transaction.UserTransaction;

import hibernatereproducer.entity.TestEntity;

/**
 * @author Jan Martiska
 */
@WebServlet(urlPatterns = "/")
public class ActionServlet extends javax.servlet.http.HttpServlet {

    @PersistenceContext(unitName = "MainPU")
    private EntityManager em;

    @Resource
    private UserTransaction tx;

    protected void doPost(javax.servlet.http.HttpServletRequest request,
                          javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        throw new ServletException("Only GET, dude!");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request,
                         javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        try {
            tx.begin();
            em.persist(new TestEntity());
            em.persist(new TestEntity());
            em.persist(new TestEntity());
            tx.commit();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
