package org.example.graphql;

import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.Cancellable;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;

@Path("/dynamic")
public class DynamicResource {

    private Logger LOG = Logger.getLogger(DynamicResource.class.getName());

    @Inject
    @GraphQLClient("dynamic")
    DynamicGraphQLClient client;

    @Path("/query")
    @GET
    public void query() throws ExecutionException, InterruptedException {
        Document query = document(
                operation(
                        field("all", field("name"))
                )
        );
        Response response = client.executeSync(query);
        System.out.println(response);
    }

    @Path("/query-text")
    @GET
    public void querytext() throws ExecutionException, InterruptedException {
        Response response = client.executeSync("query($namePrefix:String) {all(nameStartsWith: $namePrefix) {name}}", Collections.singletonMap("namePrefix", "d"));
        System.out.println(response);
    }

    Cancellable subscription;

    @Path("/subscription/start")
    @GET
    public void subscription() {
        Document query = document(
                operation(
                        OperationType.SUBSCRIPTION,
                        field("newPeopleShared", field("name"))
                )
        );
        Multi<Response> multi = client.subscription(query);
        subscription = multi.subscribe().with(item -> {
            LOG.info("Dynamic client received: " + item);
        }, failure -> {
            LOG.warn("Dynamic client received failure", failure);
        }, () -> {
            LOG.info("Dynamic client's subscription finished");
        });
    }

    @Path("/subscription/stop")
    @GET
    public void subscriptionStop() {
        subscription.cancel();
    }

}
