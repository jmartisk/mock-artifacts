## Usage:
```
mvn clean install
java -jar target/metrics-thorntail.jar

# generate some data
curl localhost:8080/counter
curl localhost:8080/timer
curl localhost:8080/timer2 # this should update the same metric as just 'timer'
curl localhost:8080/gauge
curl localhost:8080/meter

# read metrics
curl -H"Accept: application/json" localhost:8080/metrics
curl -H"Accept: application/json" localhost:8080/metrics/application
```
