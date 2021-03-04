## Provision a wildfly instance to use
`galleon.sh provision provision.xml --dir=wildfly`

## Configure and run WildFly
- run a wildfly instance 
```
wildfly/bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013%
/subsystem=undertow/application-security-domain=other:add(security-domain=ApplicationDomain)
```                                                                                                       

And to get Jakarta EE Security API available (optional, it's commented out in the code):
``` 
/subsystem=undertow/application-security-domain=other:write-attribute(name=enable-jacc, value=true)
/subsystem=undertow/application-security-domain=other:write-attribute(name=integrated-jaspi, value=false)
/subsystem=elytron/policy=jacc:add(jacc-policy={})
/subsystem=ee:write-attribute(name=global-modules,value=[{name=org.glassfish.soteria,services=true}])
:reload
```

## Deploy
- `mvn package wildfly:deploy`

## Query
```
USER_CREDENTIALS='-u joe:joeIsAwesome2013%'
```
- To get the schema, `curl localhost:8080/graphql/schema.graphql`
- To run a query, `curl $USER_CREDENTIALS -H"Content-Type: application/json" -X POST localhost:8080/graphql -d @queries/query-all-persons.txt`
- To run a query returning Uni, `curl $USER_CREDENTIALS -H"Content-Type: application/json" -X POST localhost:8080/graphql -d @queries/query-all-persons-uni.txt`
- To run a mutation, `curl $USER_CREDENTIALS -H"Content-Type: application/json" -X POST localhost:8080/graphql -d @queries/mutation-create-person.txt`
- To run a source query, `curl $USER_CREDENTIALS -H"Content-Type: application/json" -X POST localhost:8080/graphql -d @queries/generate-secret-tokens.txt`
