SELF odata4 consumer (consumes data from OData endpoint on the same server, publish it as a new VDB)

1. deploy DummyVDB-vdb.xml (the source vdb)
2. configure the odata4 consuming data source:

/subsystem=resource-adapters/resource-adapter=odata4-ds:add(module=org.jboss.teiid.resource-adapter.webservice)
/subsystem=resource-adapters/resource-adapter=odata4-ds/connection-definitions=self-odata4-connection:add(class-name=org.teiid.resource.adapter.ws.WSManagedConnectionFactory, jndi-name="java:/odata4ds", enabled=true, use-java-context=true)
/subsystem=resource-adapters/resource-adapter=odata4-ds/connection-definitions=self-odata4-connection/config-properties=EndPoint:add(value="http://localhost:8080/odata4/DummyVDB/DUMMY_MODEL")
/subsystem=resource-adapters/resource-adapter=odata4-ds/connection-definitions=self-odata4-connection/config-properties=SecurityType:add(value="HTTPBasic")
/subsystem=resource-adapters/resource-adapter=odata4-ds/connection-definitions=self-odata4-connection/config-properties=AuthUserName:add(value="teiidUser")
/subsystem=resource-adapters/resource-adapter=odata4-ds/connection-definitions=self-odata4-connection/config-properties=AuthPassword:add(value="pass.1234")
/subsystem=resource-adapters/resource-adapter=odata4-ds:activate

3. deploy target-vdb.xml (the target vdb which will mirror DUMMY_MODEL from DummyVDB and publish it as model odata4target)
4. test: http://localhost:8080/odata4/odata4target/odata4target/DUMMY_VIEW
