## Temporary hack until there is a Thorntail release containing sr-m 2.0
how to get SmallRye Metrics 2.0 inside: run this from the main directory (not target)

### build against a Thorntail build where MP Metrics module has a dependency on MP Config module
Use this git patch on top of Thorntail 2.3.x codebase and build it:
```
diff --git a/fractions/microprofile/microprofile-metrics/src/main/resources/modules/org/eclipse/microprofile/metrics/main/module.xml b/fractions/microprofile/microprofile-metrics/src/main/resources/modules/org/eclipse/microprofile/metrics/main/module.xml
index 34ba89369..75b1e3f7a 100644
--- a/fractions/microprofile/microprofile-metrics/src/main/resources/modules/org/eclipse/microprofile/metrics/main/module.xml
+++ b/fractions/microprofile/microprofile-metrics/src/main/resources/modules/org/eclipse/microprofile/metrics/main/module.xml
@@ -21,5 +21,6 @@
     <module name="javax.enterprise.api" />
     <module name="org.jboss.weld.core"/>
     <module name="org.jboss.weld.spi"/>
+    <module name="org.eclipse.microprofile.config.api"/>
   </dependencies>
 </module>
```

### rewrite microprofile-metrics-api-1.1.jar in thorntail.jar with ~/.m2/repository/org/eclipse/microprofile/metrics/microprofile-metrics-api/$SPEC_VERSION/microprofile-metrics-api-$SPEC_VERSION.jar
```
ORIGINAL_SPEC_VERSION='1.1'
SPEC_VERSION='2.0-SNAPSHOT'
rm -rf m2repo/io/smallrye/org/eclipse/microprofile/metrics/microprofile-metrics-api/${ORIGINAL_SPEC_VERSION}/ && \
  mkdir -p m2repo/org/eclipse/microprofile/metrics/microprofile-metrics-api/${ORIGINAL_SPEC_VERSION} &&  \
  cp ~/.m2/repository/org/eclipse/microprofile/metrics/microprofile-metrics-api/$SPEC_VERSION/microprofile-metrics-api-$SPEC_VERSION.jar m2repo/org/eclipse/microprofile/metrics/microprofile-metrics-api/${ORIGINAL_SPEC_VERSION}/microprofile-metrics-api-${ORIGINAL_SPEC_VERSION}.jar && \
  zip target/metrics-thorntail.jar m2repo/org/eclipse/microprofile/metrics/microprofile-metrics-api/${ORIGINAL_SPEC_VERSION}/microprofile-metrics-api-${ORIGINAL_SPEC_VERSION}.jar
```

### rewrite smallrye-metrics-1.1.3.jar in thorntail.jar with ~/.m2/repository/io/smallrye/smallrye-metrics/$SMALLRYE_VERSION/smallrye-metrics-$SMALLRYE_VERSION.jar
```
ORIGINAL_SMALLRYE_VERSION='1.1.3'
SMALLRYE_VERSION='2.0-SNAPSHOT'
rm -rf m2repo/io/smallrye/smallrye-metrics/${ORIGINAL_SMALLRYE_VERSION}/ && \
  mkdir -p m2repo/io/smallrye/smallrye-metrics/${ORIGINAL_SMALLRYE_VERSION}/ &&  \
  cp ~/.m2/repository/io/smallrye/smallrye-metrics/$SMALLRYE_VERSION/smallrye-metrics-$SMALLRYE_VERSION.jar m2repo/io/smallrye/smallrye-metrics/${ORIGINAL_SMALLRYE_VERSION}/smallrye-metrics-${ORIGINAL_SMALLRYE_VERSION}.jar && \
  zip target/metrics-thorntail.jar m2repo/io/smallrye/smallrye-metrics/${ORIGINAL_SMALLRYE_VERSION}/smallrye-metrics-${ORIGINAL_SMALLRYE_VERSION}.jar
```

## Usage:
```
mvn clean install
# TEMPORARY: apply the hack above
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
