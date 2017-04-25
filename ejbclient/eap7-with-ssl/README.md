### Invoking EJBs secured by SSL (using legacy PicketBox configuration, for EAP 7.0+)

1. unzip EAP distro
2. copy `ssl/server.keystore` and `ssl/server.truststore` to `$EAP_HOME/standalone/configuration`
3. add user to EAP with this command: `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
4. prepare EAP config:

```
/core-service=management/security-realm=SSLRealm:add
/core-service=management/security-realm=SSLRealm/server-identity=ssl:add(keystore-password=123456, keystore-relative-to=jboss.server.config.dir, keystore-path=server.keystore)
/core-service=management/security-realm=SSLRealm/authentication=truststore:add(keystore-path=server.keystore, keystore-relative-to=jboss.server.config.dir, keystore-password=123456)
reload
/subsystem=undertow/server=default-server/https-listener=https:add(socket-binding=https, security-realm=SSLRealm)
/subsystem=remoting/http-connector=https-remoting-connector:add(connector-ref=https, security-realm=ApplicationRealm)
```

5. build the `server` project, deploy it
6. run the `client` project using `mvn exec:exec`

