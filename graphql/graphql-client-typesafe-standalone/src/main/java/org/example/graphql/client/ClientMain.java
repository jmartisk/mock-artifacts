package org.example.graphql.client;

import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        Integer port = Integer.getInteger("port", 8080);
        // alternatively, set the url using a config property instead of passing it to the builder by calling endpoint()
        // this works in smallrye-graphql 1.3.2+ with proper support for standalone (Java SE) apps
//        System.setProperty("org.example.graphql.client.PeopleClientApi/mp-graphql/url", "http://localhost:" + port + "/graphql");
        try (PeopleClientApi client = TypesafeGraphQLClientBuilder.newBuilder()
            .endpoint("http://localhost:" + port + "/graphql")
            .build(PeopleClientApi.class)) {
            System.out.println(client.getAll());
        }
    }
}
