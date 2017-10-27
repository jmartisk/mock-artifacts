## Example how to invoke EJBs with EJB client over SSL/HTTPS with certificate authentication (no password)

1. unzip EAP distro
2. copy `ssl/server.keystore` and `ssl/server.truststore` to `$EAP_HOME/standalone/configuration`
3. prepare server config:

```
batch
/subsystem=elytron/key-store=server-key-store:add(path=server.keystore, relative-to=jboss.server.config.dir, credential-reference={clear-text=123456}, type=JKS)
/subsystem=elytron/key-store=server-trust-store:add(path=server.truststore, relative-to=jboss.server.config.dir, credential-reference={clear-text=123456}, type=JKS)
/subsystem=elytron/key-manager=example-key-manager:add(key-store=server-key-store, credential-reference={clear-text=123456})
/subsystem=elytron/trust-manager=example-trust-manager:add(key-store=server-trust-store)
/subsystem=elytron/server-ssl-context=example-ssl-context:add(trust-manager=example-trust-manager, key-manager=example-key-manager, need-client-auth=true, want-client-auth=true)
/subsystem=elytron/constant-role-mapper=constantClientRole:add(roles=[users])
/subsystem=elytron/x500-attribute-principal-decoder=CNDecoder:add(oid="2.5.4.3",maximum-segments=1)
/subsystem=elytron/key-store-realm=server-key-store-realm:add(key-store=server-trust-store)
/subsystem=elytron/security-domain=CertificateDomain:add(realms=[{realm=server-key-store-realm}], default-realm=server-key-store-realm, permission-mapper=default-permission-mapper, principal-decoder=CNDecoder, role-mapper=constantClientRole)
/subsystem=elytron/sasl-authentication-factory=certificate-sasl-authentication:add(sasl-server-factory=elytron, security-domain=CertificateDomain, mechanism-configurations=[{mechanism-name=EXTERNAL, mechanism-realm-configurations=[{realm-name=server-key-store-realm}]}])
/subsystem=undertow/server=default-server/https-listener=https:undefine-attribute(name=security-realm)
/subsystem=undertow/server=default-server/https-listener=https:write-attribute(name=ssl-context,value=example-ssl-context)
/subsystem=remoting/http-connector=https-remoting-connector:add(connector-ref=https, sasl-authentication-factory=certificate-sasl-authentication)
/subsystem=ejb3/application-security-domain=other:add(security-domain=CertificateDomain)
run-batch
reload
```

4. build server side, deploy
5. run client side using ```mvn exec:exec -Dremote.ejb.url=$URL```

default $URL is remote+https://127.0.0.1:8443


