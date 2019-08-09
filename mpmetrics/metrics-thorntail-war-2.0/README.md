## Usage:
```
mvn clean install
java -jar target/metrics-thorntail.jar

# generate some data for metrics from annotated methods
curl localhost:8080/counter
curl localhost:8080/cgauge
curl localhost:8080/timer
curl localhost:8080/timer2 # this should update the same metric as just 'timer'
curl localhost:8080/gauge
curl localhost:8080/meter

# generate data for metrics from CDI producer fields
curl localhost:8080/producerfield/counter
curl localhost:8080/producerfield/timer
curl localhost:8080/producerfield/gauge
curl localhost:8080/producerfield/meter
curl localhost:8080/producerfield/histogram

# generate data for metrics from CDI producer methods
curl localhost:8080/producermethod/counter
curl localhost:8080/producermethod/timer
curl localhost:8080/producermethod/gauge
curl localhost:8080/producermethod/meter
curl localhost:8080/producermethod/histogram

# read metrics
curl -H"Accept: application/json" localhost:8080/metrics
curl -H"Accept: application/json" localhost:8080/metrics/application
```
