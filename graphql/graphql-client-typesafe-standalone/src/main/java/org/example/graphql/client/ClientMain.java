package org.example.graphql.client;

import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;

public class ClientMain {

    public static void main(String[] args) {
        Integer port = Integer.getInteger("port", 8080);
        PeopleClientApi client = TypesafeGraphQLClientBuilder.newBuilder()
                .endpoint("http://localhost:" + port + "/graphql")
                .build(PeopleClientApi.class);
        System.out.println(client.getAll());
    }
}
