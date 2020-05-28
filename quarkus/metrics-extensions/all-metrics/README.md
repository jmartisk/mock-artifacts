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

Running Prometheus and Grafana in Docker:
```
CONFIG=/path/to/prometheus/config.yml
docker run --rm --network=host -p 9090:9090 -v $CONFIG:/etc/prometheus/prometheus.yml prom/prometheus
docker run --rm -p 3000:3000 --network=host grafana/grafana
```
