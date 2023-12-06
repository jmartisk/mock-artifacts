package org.acme.shop;

import io.quarkus.arc.Unremovable;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.SecurityIdentityAugmentor;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;

@ApplicationScoped
@Unremovable
public class SecurityAugmentor implements SecurityIdentityAugmentor {

    @Inject
    CurrentVertxRequest vertxRequest;

    @Override
    @ActivateRequestContext
    public Uni<SecurityIdentity> augment(SecurityIdentity identity, AuthenticationRequestContext context) {
        System.out.println("00000000000000000000000000000000000000000000000");
        System.out.println(vertxRequest.getCurrent());
        return Uni.createFrom().item(identity);
    }
}
