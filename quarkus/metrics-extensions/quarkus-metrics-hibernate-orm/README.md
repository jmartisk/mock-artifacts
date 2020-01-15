# run database 
docker run --rm -it -p 5432:5432 -e POSTGRES_PASSWORD=12345 postgres:12.1

# run app
mvn quarkus:dev
mvn package
mvn package -Pnative

# trigger some business logic
curl localhost:8080/create

# check metrics
curl -H"Accept: application/json" localhost:8080/metrics/vendor | grep hibernate