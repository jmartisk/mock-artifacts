- run standalone-microprofile.xml profile!
- deploy into wildfly
- generate some data:
curl -s localhost:8080/metrics-wildfly-1.0-SNAPSHOT/counter
- read metrics:
curl -s localhost:9990/metrics/application
