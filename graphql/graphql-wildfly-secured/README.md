## Provision a wildfly instance to use
`galleon.sh provision provision.xml --dir=wildfly`

## Configure and run WildFly
- run a wildfly instance 
```
wildfly/bin/add-user.sh -a -g users -u joe -p joeIsAwesome2013%
/subsystem=elytron/http-authentication-factory=example-http-auth:add(http-server-mechanism-factory=global,security-domain=ApplicationDomain,mechanism-configurations=[{mechanism-name=BASIC,mechanism-realm-configurations=[{realm-name=exampleApplicationDomain}]}])
/subsystem=undertow/application-security-domain=UDomain:add(http-authentication-factory=example-http-auth)
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
