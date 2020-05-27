Quarkus application containing a lot of various metrics
This is used mainly for developing the Quarkus Grafana dashboard

To run two instances of this:
`mvn package`
and then in two terminals:
`java -Dquarkus.http.port=8080 -jar target/quarkus-many-metrics-runner.jar`
`java -Dquarkus.http.port=8081 -jar target/quarkus-many-metrics-runner.jar`

Generate some data:
`sh generate-data.sh`

Example Prometheus configuration:
```
global:
  scrape_interval:     5s
  evaluation_interval: 5s

scrape_configs:                                                                 
  - job_name: 'local-server-name'
    static_configs:
    - targets: ['localhost:8080', 'localhost:8081']
```