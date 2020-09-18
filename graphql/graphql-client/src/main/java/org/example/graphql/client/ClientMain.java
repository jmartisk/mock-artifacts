package org.example.graphql.client;

import io.smallrye.graphql.client.typesafe.api.GraphQlClientBuilder;

public class ClientMain {

    public static void main(String[] args) {
        PeopleClientApi client = GraphQlClientBuilder.newBuilder()
                .endpoint("http://localhost:8080/graphql")
                .build(PeopleClientApi.class);
        System.out.println(client.getAll());
    }
}
