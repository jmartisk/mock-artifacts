package org.acme.shop;

import graphql.GraphQLContext;
import graphql.schema.Coercing;
import graphql.schema.CoercingSerializeException;
import io.quarkus.arc.Unremovable;
import io.smallrye.graphql.api.CustomScalar;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@CustomScalar("XXX")
@ApplicationScoped
@Unremovable
//@Default
public class MyCustomScalar implements Coercing<MyCustomScalar, String> {

    @Override
    public @Nullable String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
        return dataFetcherResult.toString();
    }
}
