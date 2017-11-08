## Invoking EJBs in a server-to-server scenario using Elytron on the client side
### 1. Prepare server-side EAP
1. Add the application user:
```
bin/add-user.sh -a -g users -u admin -p admin123+
```
2. Run EAP
3. Build and deploy the `server` project

### 2. Prepare client-side EAP
1. Add the application user:
```
bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!
```
2. Run the EAP with property `-Dremote.ejb.host=HOSTNAME_OF_REMOTE_SERVER` (where `HOSTNAME_OF_REMOTE_SERVER` is the address where server-side EAP is available)
3. Configure the things needed for the EJB client connection:
```
/socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=remote-ejb:add(host=${remote.ejb.host}, port=8080)
/subsystem=elytron/authentication-configuration=admin-cfg:add(sasl-mechanism-selector="DIGEST-MD5", credential-reference={clear-text="admin123+"}, authentication-name=admin, realm=ApplicationRealm)
/subsystem=elytron/authentication-context=admin-ctx:add(match-rules=[{authentication-configuration=admin-cfg}])
/subsystem=remoting/remote-outbound-connection=remote-ejb-connection:add(authentication-context=admin-ctx, outbound-socket-binding-ref=remote-ejb)
```

3. Build and deploy the `intermediate` project

### 3. Run the example
1. Build and run the `client` project: `mvn clean install exec:exec -Dintermediate.ejb.host=HOSTNAME_OF_INTERMEDIATE_SERVER` (where `HOSTNAME_OF_INTERMEDIATE_SERVER` is EAP with `intermediate` deployment).
