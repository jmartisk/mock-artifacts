import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import model.Geocache;

public class Reindex {

    public static void main(String[] args) throws InterruptedException {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MainPU");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final FullTextEntityManager ftem = Search.getFullTextEntityManager(entityManager);
        ftem.createIndexer(Geocache.class)
                .startAndWait();     // reindex in case that this entity is currently not in the index
        ftem.close();
        entityManagerFactory.close();
    }

}
