## Run in dev mode:

```
mvn quarkus:dev
```

## Before running in prod mode:
```
docker run --rm --name postgres   \
    -e POSTGRES_PASSWORD=postgres \
    -e POSTGRES_USER=postgres     \
    -e POSTGRES_DB=postgres       \
    -p 5432:5432                  \
    docker.io/library/postgres:12.2
```

## To enable basic security:

Uncomment the relevant properties in `application.properties`.
Add `-u alice:alice` to the curl command to authenticate.

## Call the endpoint:

```
curl  -v localhost:8080/graphql -d'{"query": "{customers{name}}"}'
```