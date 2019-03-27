(temporary hack until there is a thorntail release containing sr-m 2.0)
how to get SmallRye Metrics 2.0 inside: run this from the main directory (not target)

# rewrite microprofile-metrics-api-1.1.jar in thorntail.jar with ~/.m2/repository/org/eclipse/microprofile/metrics/microprofile-metrics-api/$SPEC_VERSION/microprofile-metrics-api-$SPEC_VERSION.jar
ORIGINAL_SPEC_VERSION='1.1'
SPEC_VERSION='2.0-SNAPSHOT'
rm -rf m2repo/io/smallrye/org/eclipse/microprofile/metrics/microprofile-metrics-api/${ORIGINAL_SPEC_VERSION}/ && \
  mkdir -p m2repo/org/eclipse/microprofile/metrics/microprofile-metrics-api/${ORIGINAL_SPEC_VERSION} &&  \
  cp ~/.m2/repository/org/eclipse/microprofile/metrics/microprofile-metrics-api/$SPEC_VERSION/microprofile-metrics-api-$SPEC_VERSION.jar m2repo/org/eclipse/microprofile/metrics/microprofile-metrics-api/${ORIGINAL_SPEC_VERSION}/microprofile-metrics-api-${ORIGINAL_SPEC_VERSION}.jar && \
  zip target/metrics-thorntail.jar m2repo/org/eclipse/microprofile/metrics/microprofile-metrics-api/${ORIGINAL_SPEC_VERSION}/microprofile-metrics-api-${ORIGINAL_SPEC_VERSION}.jar

# rewrite smallrye-metrics-1.1.3.jar in thorntail.jar with ~/.m2/repository/io/smallrye/smallrye-metrics/$SMALLRYE_VERSION/smallrye-metrics-$SMALLRYE_VERSION.jar
ORIGINAL_SMALLRYE_VERSION='1.1.3'
SMALLRYE_VERSION='2.0-SNAPSHOT'
rm -rf m2repo/io/smallrye/smallrye-metrics/${ORIGINAL_SMALLRYE_VERSION}/ && \
  mkdir -p m2repo/io/smallrye/smallrye-metrics/${ORIGINAL_SMALLRYE_VERSION}/ &&  \
  cp ~/.m2/repository/io/smallrye/smallrye-metrics/$SMALLRYE_VERSION/smallrye-metrics-$SMALLRYE_VERSION.jar m2repo/io/smallrye/smallrye-metrics/${ORIGINAL_SMALLRYE_VERSION}/smallrye-metrics-${ORIGINAL_SMALLRYE_VERSION}.jar && \
  zip target/metrics-thorntail.jar m2repo/io/smallrye/smallrye-metrics/${ORIGINAL_SMALLRYE_VERSION}/smallrye-metrics-${ORIGINAL_SMALLRYE_VERSION}.jar

usage:
mvn clean install
# TEMPORARY: apply the hack above
java -jar target/metrics-thorntail.jar
curl localhost:8080/counter
curl localhost:8080/cgauge
curl -H"Accept: application/json" localhost:8080/metrics
curl -H"Accept: application/json" localhost:8080/metrics/application
