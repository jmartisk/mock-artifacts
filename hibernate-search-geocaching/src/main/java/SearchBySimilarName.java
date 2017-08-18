import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import model.Geocache;

/**
 * @author Jan Martiska
 */
public class SearchBySimilarName {

    public static void main(String[] args) throws InterruptedException {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MainPU");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final FullTextEntityManager ftem = Search.getFullTextEntityManager(entityManager);
        final org.hibernate.search.query.dsl.QueryBuilder queryBuilder = ftem.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Geocache.class).get();
        try {
            // find a reference one
            final Geocache ref = entityManager.find(Geocache.class, "GC1GN1Z");// Bridlicova 1 - Muzeum

            final Query luceneQuery = queryBuilder.moreLikeThis()
                    .excludeEntityUsedForComparison()
                    .comparingField("name")
                    .toEntity(ref)
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
