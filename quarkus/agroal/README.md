Perform some operations with data sources:
`curl localhost:8080`
`curl localhost:8080/other`
Metrics will be exposed at
`curl localhost:8080/metrics`

By default runs with H2. H2 cannot be compiled into native mode.
To use native mode, use the postgresql profile.
Run postgresql using docker:
```
docker run --ulimit memlock=-1:-1 -it --rm=true --memory-swappiness=0 \
    --name quarkus_test -e POSTGRES_USER=username-default -e POSTGRES_PASSWORD=password \
    -e POSTGRES_DB=exampledb -p 5432:5432 postgres:10.5
```                
And use `-Dquarkus.profile=postgresql` when executing devmode or building a jar/native image.