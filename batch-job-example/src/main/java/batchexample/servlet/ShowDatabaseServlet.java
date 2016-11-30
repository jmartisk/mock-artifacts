package batchexample.servlet;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import batchexample.model.Person;

/**
 * @author Jan Martiska
 */
@WebServlet(urlPatterns = "/show")
public class ShowDatabaseServlet extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final TypedQuery<Person> personQuery = em.createQuery("from Person p", Person.class);
        personQuery.getResultList().forEach(person -> {
            try {
                resp.getWriter().append(person.toString() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }


}
