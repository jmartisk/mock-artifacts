## EJB client over HTTP transport invoking clustered beans over an Undertow load balancer
### 1. prepare undertow load balancer node
1. use the config from `docs/examples/standalone-load-balancer.xml`
2. Then apply this:
```
/subsystem=undertow/configuration=handler/reverse-proxy=ejb:add
/subsystem=undertow/server=default-server/host=default-host/location=wildfly-services:add(handler=ejb)
/subsystem=undertow/server=default-server/host=default-host/location=wildfly-services/filter-ref=load-balancer:add
```

### 2. prepare worker nodes
For each worker node, this needs to be done:
1. Add the user who will perform the invocations: `bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013!`
2. Enable the HTTP invoker:
```
/subsystem=undertow/server=default-server/host=default-host/setting=http-invoker:add(http-authentication-factory=application-http-authentication)
/subsystem=ejb3/application-security-domain=other:add(security-domain=ApplicationDomain)
```
3. build the `server` project and deploy it to all nodes
4. run the workers with something like this...
`bin/standalone.sh -b 127.0.0.2 -bmanagement=127.0.0.2 -Djboss.node.name=node2 -c standalone-ha.xml`

### 3. run the example
1. run the `client` project using:
```
mvn exec:exec -Dstateful=(true|false) -Dremote.server.address=ADDRESS_OF_LOAD_BALANCER_NODE 
```
You can also specify the address of a worker node instead of load balancer, then it will invoke only on that node.

The `-Dstateful` property chooses whether you want to invoke a stateful or a stateless bean.
