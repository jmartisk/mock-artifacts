import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.Unit;

import model.Geocache;

/**
 * @author Jan Martiska
 */
public class SearchNearestByCoordinates {

    // find caches within 12 km from [49.77N, 17.53E]
    public static void main(String[] args) throws InterruptedException {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MainPU");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final FullTextEntityManager ftem = Search.getFullTextEntityManager(entityManager);
        ftem.createIndexer(Geocache.class).startAndWait();     // reindex in case that this entity is currently not in the index

        final org.hibernate.search.query.dsl.QueryBuilder queryBuilder = ftem.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Geocache.class).get();
        try {
            final Query luceneQuery = queryBuilder.spatial()
                    .withConstantScore()
                    .within(12, Unit.KM).ofLatitude(49.77)
                    .andLongitude(17.53)
                    .createQuery();
            final FullTextQuery fullTextQuery = ftem.createFullTextQuery(luceneQuery, Geocache.class);
            final List<Geocache> gcs = fullTextQuery.getResultList();
            System.out.println(gcs.size());
            gcs.forEach(System.out::println);
        } finally {
            ftem.close();
            entityManagerFactory.close();
        }
    }
}
