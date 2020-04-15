Run Jaeger
```
docker run -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 jaegertracing/all-in-one:1.17
```

Run the app
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

Check the tracing data
```
http://localhost:16686/
```
