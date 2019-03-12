Options to run this
-------------------
Development mode:
- `mvn compile quarkus:dev`
- This also opens debugging on port 5005

Classic JAR:
- `mvn package && java -jar target/quarkus-metrics-runner.jar`

Native binary:
- set `GRAALVM_HOME` env variable
- `mvn package -Pnative && target/quarkus-metrics-runner`

Docker image with native binary:
- start Docker
- set `GRAALVM_HOME` env variable
- `mvn package -Pnative -Dnative-image.docker-build=true`
- `docker build -t quarkus-quickstart/quickstart .`
- `docker run -i --rm -p 8080:8080 quarkus-quickstart/quickstart`

OpenShift, binary build from local dir, containing the native binary:
- set `GRAALVM_HOME` env variable
- `mvn package -Pnative -Dnative-image.docker-build=true`
- `oc new-project quarkus-metrics`
- `oc new-build --binary --name=quarkus-metrics -l app=quarkus-metrics`
- `oc start-build quarkus-metrics --from-dir=. --follow`
- `oc new-app --image-stream=quarkus-metrics:latest`
- `oc expose service quarkus-metrics`
- `export URL="http://$(oc get route | grep quarkus-metrics | awk '{print $2}')"`
- `curl $URL`

Accessing the app
-----------------
- `curl localhost:8080`
- `curl localhost:8080/metrics`

Running the tests
-----------------
Just the JVM test:
`mvn test -Dtest=CounterTest`
Both tests:
`mvn verify -Pnative -Dtest=CounterTest -Dit.test=CounterIT`
