### EJB client over HTTP transport

1. unzip EAP distro
2. add user to EAP with this command:  `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`

3. If you want to use legacy security on the server side, no configuration changes are needed. If you want to use Elytron:

```
/subsystem=undertow/server=default-server/host=default-host/setting=http-invoker:undefine-attribute(name=security-realm)
/subsystem=undertow/server=default-server/host=default-host/setting=http-invoker:write-attribute(name=http-authentication-factory,value=application-http-authentication)
/subsystem=ejb3/application-security-domain=other:add(security-domain=ApplicationDomain)
```

4. build the `server` project, deploy it
5. run client side using `mvn package exec:exec`

