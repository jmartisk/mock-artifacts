package example.quarkusmetrics.test.vm;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

@QuarkusTest
public class CounterTest {

    @Test
    public void testCounter() {
        given()
                .when().get("/counter")
                .then()
                .statusCode(200)
                .body(is("OK"));
        given()
                .when().get("/metrics")
                .then()
                .statusCode(200)
                .body(containsString("application_counter_total{foo=\"bar\"} 1.0"));
    }

    // TODO: tests for other metrics

}
