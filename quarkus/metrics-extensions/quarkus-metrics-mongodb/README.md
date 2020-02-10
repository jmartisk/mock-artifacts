## Run MongoDB with Docker
```
docker run -ti --rm -p 27017:27017 mongo:4.0
```

## Run the project
```
mvn quarkus:dev
```

## Perform some operations
```
curl localhost:8080/create
curl localhost:8080/get
```

## Check the metrics
```
curl localhost:8080/metrics/vendor | grep mongo
```