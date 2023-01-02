///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS io.smallrye:smallrye-graphql-client-implementation-vertx:2.0.0

import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.vertx.dynamic.VertxDynamicGraphQLClientBuilder;

import io.vertx.core.Vertx;

// Has a multiline string literal, requires Java 15+!
class Client {

    public static void main(String... args) throws Exception {
        Vertx vertx = Vertx.vertx();
        if(args.length == 0) {
            System.out.println("Please provide a github access token as an argument of this script");
            return;
        }
        String ghToken = args[0];
        DynamicGraphQLClient client = new VertxDynamicGraphQLClientBuilder()
            .url("https://api.github.com/graphql")
            .header("Authorization", String.format("Bearer %s", ghToken))
            .vertx(vertx)
            .build();
        try {
            Response response = client.executeSync("""
                query {
                  viewer {
                    login
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

