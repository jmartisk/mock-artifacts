## Usage:
```
mvn clean install
java -jar target/metrics-thorntail.jar

# generate some data
curl localhost:8080/counter

# read metrics
curl -H"Accept: application/json" localhost:8080/metrics
curl -H"Accept: application/json" localhost:8080/metrics/application
```
