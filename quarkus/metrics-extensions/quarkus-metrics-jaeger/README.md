Run
```
mvn quarkus:dev
```

Generate some data
```
curl localhost:8080
```

Check the metrics
```
curl localhost:8080/metrics/ |  grep jaeger
```