# GraphQL client based on Quarkus that connects to Hasura and uses subscriptions


## Prepare Hasura 

### Run Hasura and database
```
podman-compose up
# hasura UI is now available at http://localhost:7777/console
# postgres database is now available at localhost:5432 
# now add a datasource in the UI: http://localhost:7777/console/data/manage/connect
# db url should be postgres://postgres:postgrespassword@postgres:5432/postgres
```

### Add some testing data
```
# (install postgresql client using sudo dnf install postgresql) 
psql -h localhost -p 5432 -U postgres -f import-data.sql
```

## Run the client

To change the used protocol, update `application.properties`
```
quarkus.smallrye-graphql-client.DYNAMIC.subprotocols=         # graphql-ws | graphql-transport-ws
```

### Manually using wscat
`graphql-ws`:
```
wscat -w 300 -P -s graphql-ws -c "ws://localhost:7777/v1/graphql"
{"type":"connection_init","payload":{"headers":{"content-type":"application/json"},"lazy":true}}
{"id":"1","type":"start","payload":{"variables":{},"extensions":{},"operationName":"x","query":"subscription x {person {id name age}}"}}
{"id":"1","type":"stop"}
```

`graphql-transport-ws`:
```
wscat -w 300 -P -s graphql-transport-ws -c "ws://localhost:7777/v1/graphql"
{"type":"connection_init"}
{"id": "1","type":"subscribe","payload":{"query":"subscription x { person {id name age} }","operationName":"x","variables":{}}}
{"id":"1","type":"complete"}

```

### Typesafe client
```
curl localhost:8080/typesafe/start
curl localhost:8080/typesafe/stop
```

### Dynamic client
```
curl localhost:8080/dynamic/start
curl localhost:8080/dynamic/stop
```

## Do changes to the database to receive them by the client
```
psql -h localhost -p 5432 -U postgres -f generate-event.sql
```

## Cleanup
```
psql -h localhost -p 5432 -U postgres -f clear-data.sql # not necessary though if you want to keep the data for next time
podman-compose down
```
