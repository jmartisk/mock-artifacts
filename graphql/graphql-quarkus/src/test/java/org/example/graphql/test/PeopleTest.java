package org.example.graphql.test;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.helpers.test.AssertSubscriber;
import org.example.graphql.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@QuarkusTest
public class PeopleTest {

    @GraphQLClient("people")
    DynamicGraphQLClient client;

    @Test
    public void getAll() throws ExecutionException, InterruptedException {
        Document document = Document.document(
            operation(
                field("all",
                    field("name"),
                    field("gender")))
        );
        Response response = client.executeSync(document);
        List<Person> list = response.getList(Person.class, "all");
        Assertions.assertTrue(list.size() > 0);
        System.out.println("---- TEST OUTPUT: " + list);
    }

    @Test
    public void testSingleResultMulti() {
        Document document = Document.document(
            operation(
                OperationType.SUBSCRIPTION,
                field("singleResultMulti",
                    field("name"),
                    field("gender")))
        );
        Multi<Response> responseMulti = client.subscription(document);

        AssertSubscriber<Response> subscriber = new AssertSubscriber<>(100);
        responseMulti.subscribe().withSubscriber(subscriber);

        subscriber.awaitCompletion(Duration.ofSeconds(2));
        List<Response> items = subscriber.getItems();

        Assertions.assertEquals(1, items.size());

        System.out.println("---- TEST OUTPUT: " + items.stream().map(
            response -> response.getObject(Person.class, "singleResultMulti"))
                .collect(Collectors.toList()));
    }
}
