package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("/")
public class HelloResource {

    @Inject
    MongoClient mongoClient;

    @Path("/create")
    @GET
    public void createPersons() {
        MongoCollection<Document> collection = mongoClient.getDatabase("dummydb").getCollection("person");
        Document document = new Document()
                .append("name", "joe");
        collection.insertOne(document);
        document = new Document()
                .append("name", "david");
        collection.insertOne(document);
    }

    @Path("/get")
    @GET
    @Produces("application/json")
    public List<Person> getPersons() {
        List<Person> result = new ArrayList<>();
        MongoCursor<Document> cursor = mongoClient.getDatabase("dummydb").getCollection("person").find().iterator();
        cursor.forEachRemaining(document -> {
            result.add(new Person(document.getString("name")));
        });
        return result;
    }

}
