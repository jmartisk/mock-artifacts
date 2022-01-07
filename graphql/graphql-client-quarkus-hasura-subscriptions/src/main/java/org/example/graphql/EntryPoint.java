package org.example.graphql;

import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;
import io.smallrye.graphql.client.websocket.WebsocketSubprotocol;
import io.smallrye.mutiny.Multi;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/")
public class EntryPoint {

    private Logger logger = Logger.getLogger(EntryPoint.class);

    private PersonApi typesafeClient;
    private Multi<List<Person>> personMulti;

    @Path("/typesafe/start")
    @GET
    public void startTypesafeClient() {
        typesafeClient = TypesafeGraphQLClientBuilder.newBuilder()
            .endpoint("http://localhost:7777/v1/graphql")
            .subprotocols(WebsocketSubprotocol.GRAPHQL_TRANSPORT_WS)
            .build(PersonApi.class);
        personMulti = typesafeClient.person();
        personMulti.subscribe().with(list -> {
            logger.info("Typesafe client received: " + list);
        }, failure -> {
            System.out.println("APPLICATION RECEIVED A FAILURE");
            failure.printStackTrace();
        });
    }

    // TODO: this should do typesafeClient.close or something
//    @Path("/typesafe/stop")
//    @GET
//    public void stopTypesafeClient() {
//
//    }
}
