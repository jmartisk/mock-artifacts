import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.facet.FacetSortOrder;

import model.Geocache;

public class SearchByCreatedDate {


    public static void main(String[] args) throws InterruptedException {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MainPU");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final FullTextEntityManager ftem = Search.getFullTextEntityManager(entityManager);
        final org.hibernate.search.query.dsl.QueryBuilder queryBuilder = ftem.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Geocache.class).get();
        try {
            // find caches places earlier than 1.1.2002
            final Query luceneQuery = queryBuilder
                    .range()
                    .onField("placedDate")
                    .below(new GregorianCalendar(2002, 1, 1))
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
