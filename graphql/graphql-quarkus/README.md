## Run
`mvn quarkus:dev`

## Query
Use GraphiQL interface at `http://localhost:8080/graphql-ui`
Or use curl:
- To get the schema, `curl localhost:8080/graphql/schema.graphql`
- To run a query, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql/ -d @queries/query-all-persons.txt`
- To run a mutation, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql/ -d @queries/mutation-create-person.txt`
- To run a source query, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql/ -d @queries/generate-secret-tokens.txt`

## Metrics
`curl localhost:8080/metrics/vendor | grep graphql`
