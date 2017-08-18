import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.xml.bind.DatatypeConverter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import model.Geocache;

/**
 * @author Jan Martiska
 */
public class DatabaseFiller {

    public static final String GROUNDSPEAK_NAMESPACE = "http://www.groundspeak.com/cache/1/0/1";
    public static final String TOPOGRAFIX_NAMESPACE = "http://www.topografix.com/GPX/1/0";

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {


        final List<Geocache> geocaches = parseCaches("src/main/resources/2529264.gpx");
        System.out.println("Found " + geocaches.size() + " geocaches in the gpx file");


        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MainPU");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            final EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            geocaches.forEach(entityManager::merge);
            System.out.println("Committing");
            tx.commit();
            System.out.println("Committed");


            final TypedQuery<Geocache> emAllQuery = entityManager
                    .createQuery("from Geocache", Geocache.class);
            System.out.println("DB size: " + emAllQuery.getResultList().size());
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    public static List<Geocache> parseCaches(String filePath) throws FileNotFoundException,
            XMLStreamException {
        final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        xmlInputFactory.setProperty("javax.xml.stream.isCoalescing",
                true);     // to properly read HTML code in descriptions
        final XMLStreamReader reader = xmlInputFactory
                .createXMLStreamReader(new FileReader(filePath));
        final List<Geocache> result = new ArrayList<>();

        State state = State.OUT_CACHE;
        Geocache current = null;

        while (reader.hasNext()) {
            reader.next();

            switch (state) {
            case OUT_CACHE: {
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT && reader.getName()
                        .getLocalPart().equals("wpt")) {
                    state = State.IN_CACHE;
                    current = new Geocache();
                    final String latitude = reader.getAttributeValue("", "lat");
                    final String longitude = reader.getAttributeValue("", "lon");
                    current.setLatitude(Double.parseDouble(latitude));
                    current.setLongitude(Double.parseDouble(longitude));
                }
                break;
            }
            case IN_CACHE: {
                if (reader.getEventType() == XMLStreamConstants.END_ELEMENT && reader.getName().getLocalPart()
                        .equals("wpt")) {
                    result.add(current);
                    System.out.println(current);
                    current = null;
                    state = State.OUT_CACHE;
                    break;
                }
                if (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
                    if (reader.getName().getLocalPart().equals("name")) {
                        final String namespaceURI = reader.getName().getNamespaceURI();
                        reader.next();
                        if (namespaceURI.equals(GROUNDSPEAK_NAMESPACE)) {
                            current.setName(reader.getText());
                        } else if (namespaceURI.equals(TOPOGRAFIX_NAMESPACE)) {
                            current.setGcCode(reader.getText());
                        }
                    } else if (reader.getName().getLocalPart().equals("long_description")) {
                        final String namespaceURI = reader.getName().getNamespaceURI();
                        reader.next();
                        if (namespaceURI.equals(GROUNDSPEAK_NAMESPACE)) {
                            final String desc = new String(reader.getTextCharacters());
                            current.setLongDescription(desc);
                        }
                    } else if (reader.getName().getLocalPart().equals("time")) {
                        final String namespaceURI = reader.getName().getNamespaceURI();
                        reader.next();
                        if (namespaceURI.equals(TOPOGRAFIX_NAMESPACE)) {
                            final String dateString = reader.getText();
                            DatatypeConverter.parseDate(dateString);
                            current.setPlacedDate(DatatypeConverter.parseDate(dateString).getTime());
                        }
                    }

                }
                break;

            }
            }
        }
        return result;


    }

    private enum State {
        IN_CACHE,
        OUT_CACHE
    }


}
