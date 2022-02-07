## Run
`mvn quarkus:dev`

## Query
Use GraphiQL interface at `http://localhost:8080/graphql-ui`
Or use curl:
- To get the schema, `curl localhost:8080/graphql/schema.graphql`
- To run a query, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql/ -d @queries/query-all-persons.txt`
- To run a mutation, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql/ -d @queries/mutation-create-person.txt`
- To run a source query, `curl -H"Content-Type: application/json" -X POST localhost:8080/graphql/ -d @queries/generate-secret-tokens.txt`
- Subscription: `wscat -w 30 -c "ws://localhost:8080/graphql" -x '{"query":"subscription { newPeople { name } }"}'`


## Metrics
`curl -s localhost:8080/metrics/vendor | grep graphql`

## OpenTracing
`docker run -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 jaegertracing/all-in-one:1.17`
Look at http://localhost:16686


## Using websocket transport through wscat
```
wscat -w 300 -P -s graphql-transport-ws -c "ws://localhost:8080/graphql"
{"type":"connection_init"}

# Query
{"type":"subscribe","id":"q","payload":{"query":"{all{name gender secretToken}}"}}

# Subscription
{"type":"subscribe","id":"1","payload":{"query":"subscription newPeople { newPeople {name gender} }","operationName":"newPeople","variables":{}}}
{"id":"1","type":"complete"}
```
