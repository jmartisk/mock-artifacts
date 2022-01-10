package org.example.graphql;

import io.smallrye.graphql.client.GraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;
import io.smallrye.graphql.client.websocket.WebsocketSubprotocol;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.Cancellable;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/")
public class EntryPoint {

    private Logger logger = Logger.getLogger(EntryPoint.class);

    // -------------------------------------- TYPESAFE CLIENT

    @Inject
    PersonApi typesafeClient;

    private Cancellable typesafeClientSubscription;
    private Multi<List<Person>> typesafeMulti;

    @Path("/typesafe/start")
    @GET
    public void startTypesafeClient() {
        typesafeMulti = typesafeClient.person();
        typesafeClientSubscription = typesafeMulti.subscribe().with(list -> {
            logger.info("Typesafe client received: " + list);
        }, failure -> {
            System.out.println("APPLICATION RECEIVED A FAILURE");
            failure.printStackTrace();
        });
    }

    @Path("/typesafe/stop")
    @GET
    public void stopTypesafeClient() {
        typesafeClientSubscription.cancel();
    }

    // -------------------------------------- DYNAMIC CLIENT

    @Inject
    @GraphQLClient("DYNAMIC")
    DynamicGraphQLClient dynamicClient;

    private Cancellable dynamicClientSubscription;
    private Multi<Response> dynamicMulti;

    @Path("/dynamic/start")
    @GET
    public void startDynamicClient() {
        dynamicMulti = dynamicClient.subscription("subscription person { person {id name age} }");
        dynamicClientSubscription = dynamicMulti.subscribe().with(list -> {
            logger.info("Dynamic client received: " + list);
        }, failure -> {
            System.out.println("APPLICATION (DYNAMIC CLIENT) RECEIVED A FAILURE");
            failure.printStackTrace();
        });
    }

    @Path("/dynamic/stop")
    @GET
    public void stopDynamicClient() {
        dynamicClientSubscription.cancel();
    }

}
