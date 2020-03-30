## Run
`mvn package && java -jar target/graphql-thorntail-thorntail.jar`

## Query
Use GraphiQL interface at `http://localhost:8080/graphiql/`
Or use curl:
- To get the schema, `curl localhost:8080/graphql/schema.graphql`
- To run a query, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql/ -d @queries/query-all-persons.txt`