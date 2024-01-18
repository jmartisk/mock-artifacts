package org.acme.shop;

import io.smallrye.graphql.api.ErrorExtensionProvider;
import jakarta.json.JsonValue;

public class MyErrorExtensionProvider implements ErrorExtensionProvider {
    @Override
    public String getKey() {
        return "key";
    }

    @Override
    public JsonValue mapValueFrom(Throwable throwable) {
        System.out.println("mapping from " + throwable);
        return JsonValue.FALSE;
    }
}
