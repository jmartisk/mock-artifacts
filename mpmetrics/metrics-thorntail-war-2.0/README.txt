mvn clean install
java -jar target/metrics-thorntail.jar
curl localhost:8080     # this will register the metrics and add some data
curl -H"Accept: application/json" localhost:8080/metrics
curl -H"Accept: application/json" localhost:8080/metrics/application
