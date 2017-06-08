## Invoking EJBs in a server-to-server scenario using legacy style configuration with SSL

### 1. Prepare server-side EAP
1. Copy the  server keystore, truststore and properties files from the `configuration/server` directory.
2. Run the server instance and set it up with the provided JBoss CLI scripts.
    * For one way SSL authentication, use `remoting-ssl-one-way-server.cli`.
    * For two way SSL authentication, use `remoting-ssl-two-way-server.cli`.
3. Build and deploy the `server` project.

### 2. Prepare client-side EAP
1. Copy the the client keystore and truststore from the `configuration/client` directory.
2. Run the EAP with property `-Dremote.ejb.host=HOSTNAME_OF_REMOTE_SERVER` (where `HOSTNAME_OF_REMOTE_SERVER` is the address where server-side EAP is available) and bind it to different loopback address with `-b 127.0.0.8 -bmanagement 127.0.0.8`.
3. Set the security configuration up with the provided JBoss CLI scripts.
    * For one way SSL authentication, use `remoting-ssl-one-way-client.cli`.
    * For two way SSL authentication, use `remoting-ssl-two-way-client.cli`.
4. Build and deploy the `client` project.

### 3. Run the example
1. Run the client by accessing `http://127.0.0.8:8080/client-side/` (`127.0.0.8` is the client-side EAP!)
