## Provision a wildfly instance to use
`galleon.sh provision provision.xml --dir=wildfly`

## Run
- run a wildfly instance 
- `mvn package wildfly:deploy`

To enable various stuff, add this to wildfly:
- `-Dsmallrye.graphql.validation.enabled=true`   
- `-Dsmallrye.graphql.metrics.enabled=true` 

## Query
Use GraphiQL interface at `http://localhost:8080/graphql-ui`
Or use curl:
- To get the schema, `curl localhost:8080/graphql/schema.graphql`
- To run a query, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql -d @queries/query-all-persons.txt`
- To run a query returning Uni, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql -d @queries/query-all-persons-uni.txt`
- To run a mutation, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql -d @queries/mutation-create-person.txt`
- To run a source query, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql -d @queries/generate-secret-tokens.txt`
