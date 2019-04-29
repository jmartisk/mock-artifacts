package example.quarkusmetrics.test.vm;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;

@QuarkusTest
public class GaugeTest {

    @Test
    public void testGauge() {
        given()
                .when().get("/gauge")
                .then()
                .statusCode(200);
        given()
                .when().get("/metrics/application")
                .then()
                .statusCode(200)
                .body(containsString("application:annotated_gauge "));
    }

}
