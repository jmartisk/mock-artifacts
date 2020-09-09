Options to run this
-------------------
Development mode:
- `mvn compile quarkus:dev`
- This also opens debugging on port 5005

Classic JAR:
- `mvn package && java -jar target/quarkus-metrics-runner.jar`
Optionally, add this to the `mvn` command:
`-Dquarkus.debug.generated-sources-dir=generated-sources -Dquarkus.debug.generated-classes-dir=generated-classes`

Native binary:
- set `GRAALVM_HOME` env variable
- `mvn package -Pnative && target/quarkus-metrics-runner`

Docker image with JAR:
- start Docker
- set `GRAALVM_HOME` env variable
- `mvn package`
- `docker build -f src/main/docker/Dockerfile.jvm -t mock-artifacts/quarkus-metrics-jvm .`
- `docker run -i --rm -p 8080:8080 mock-artifacts/quarkus-metrics-jvm`

Docker image with native binary:
- start Docker
- set `GRAALVM_HOME` env variable
- `mvn package -Pnative -Dnative-image.docker-build=true`
- `docker build -f src/main/docker/Dockerfile.native -t mock-artifacts/quarkus-metrics-native .`
- `docker run -i --rm -p 8080:8080 mock-artifacts/quarkus-metrics-native`

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
