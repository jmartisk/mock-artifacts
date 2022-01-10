package org.example.graphql;

import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;
import io.smallrye.graphql.client.websocket.WebsocketSubprotocol;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.subscription.Cancellable;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/")
public class EntryPoint {

    private Logger logger = Logger.getLogger(EntryPoint.class);

    private PersonApi typesafeClient;
    private Cancellable typesafeClientSubscription;
    private Multi<List<Person>> typesafeMulti;

    @Path("/typesafe/start")
    @GET
    public void startTypesafeClient() {
        typesafeClient = TypesafeGraphQLClientBuilder.newBuilder()
            .endpoint("http://localhost:7777/v1/graphql")
            .subprotocols(WebsocketSubprotocol.GRAPHQL_WS)
//            .subprotocols(WebsocketSubprotocol.GRAPHQL_TRANSPORT_WS)
            .build(PersonApi.class);
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

    private DynamicGraphQLClient dynamicClient;
    private Cancellable dynamicClientSubscription;
    private Multi<Response> dynamicMulti;

    @Path("/dynamic/start")
    @GET
    public void startDynamicClient() {
        dynamicClient = DynamicGraphQLClientBuilder.newBuilder()
            .url("http://localhost:7777/v1/graphql")
            .subprotocols(WebsocketSubprotocol.GRAPHQL_WS)
//            .subprotocols(WebsocketSubprotocol.GRAPHQL_TRANSPORT_WS)
            .build();
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
