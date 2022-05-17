///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.smallrye:smallrye-graphql-client-implementation-vertx:2.0.0.RC3

import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.vertx.dynamic.VertxDynamicGraphQLClientBuilder;

import io.vertx.core.Vertx;

// Has a multiline string literal, requires Java 15+!
class Client {

    public static void main(String... args) throws Exception {
        Vertx vertx = Vertx.vertx();
        DynamicGraphQLClient client = new VertxDynamicGraphQLClientBuilder()
            .url("https://countries.trevorblades.com")
            .vertx(vertx)
            .build();
        try {
            Response response = client.executeSync("""
                query {
                  countries {
                    name
                  }
                }
                """);
            System.out.println(response);
        } finally {
            client.close();
            vertx.close();
        }
    }

}

