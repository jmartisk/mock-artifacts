package org.example.graphql;

import io.smallrye.graphql.api.Subscription;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.mutiny.Multi;

import java.io.Closeable;
import java.util.List;

@GraphQLClientApi(configKey = "TYPESAFE")
public interface PersonApi extends Closeable {

    @Subscription
    Multi<List<Person>> person();

}
