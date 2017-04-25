### EJB client over HTTP transport

1. unzip EAP distro
2. add user to EAP with this command:  `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
3. enable the HTTP invoker with these CLI commands:

```
/subsystem=undertow/server=default-server/host=default-host/setting=http-invoker:add(http-authentication-factory=application-http-authentication)
/subsystem=ejb3/application-security-domain=other:add(security-domain=ApplicationDomain)
```

4. build the `server` project, deploy it
5. run client side using `mvn exec:exec`

