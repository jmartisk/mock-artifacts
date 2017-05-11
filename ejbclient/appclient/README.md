1. unzip EAP distro
2. add user to EAP with this command:  `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
3. run EAP
4. build server side, deploy
5. build client side jar
6. run the appclient using `$EAP_HOME/bin/appclient.sh /path/to/client.jar`
   if you bound EAP to a different address than 127.0.0.1, add the appclient.sh argument --host=http-remoting://HOST:PORT (it needs to be before the path to client jar)

