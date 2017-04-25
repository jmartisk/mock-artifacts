1. unzip EAP distro
2. add user to EAP with this command:  `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
3. run EAP, bind its applicaiton interface to `127.0.0.8` (that's the default url specified in the `wildfly-config.xml`)
4. build server side, deploy
5. build client side jar
6. run the appclient using `$EAP_HOME/bin/appclient.sh -Dwildfly.config.url=/path/to/wildfly-config.xml /path/to/client.jar`
(`wildfly-config.xml` is located in the root directory)

