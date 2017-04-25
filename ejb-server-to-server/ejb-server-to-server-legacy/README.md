## Invoking EJBs in a server-to-server scenario using PicketBox-style configuration
### 1. Prepare server-side EAP
1. Add the application user:
```bin/add-user.sh -a -g users -u admin -p admin123+```
2. Run EAP
3. Build and deploy the `server` project

### 2. Prepare client-side EAP
1. Run the EAP with property `-Dremote.ejb.host=HOSTNAME_OF_REMOTE_SERVER` (where `HOSTNAME_OF_REMOTE_SERVER` is the address where server-side EAP is available)
2. Configure the things needed for the EJB client connection:
```
/socket-binding-group=standard-sockets/remote-destination-outbound-socket-binding=remote-ejb:add(host=${remote.ejb.host}, port=8080)
/core-service=management/security-realm=ejb-security-realm:add()
/core-service=management/security-realm=ejb-security-realm/server-identity=secret:add(value="cGFzc3cqcmQx")
/subsystem=remoting/remote-outbound-connection=remote-ejb-connection:add(outbound-socket-binding-ref=remote-ejb, protocol=http-remoting, security-realm=ejb-security-realm, username=admin)
```

3. Build and deploy the `client` project

### 3. Run the example
1. Run the client by accessing `http://127.0.0.1:8080/client-side/` (`127.0.0.1` is the client-side EAP!)
