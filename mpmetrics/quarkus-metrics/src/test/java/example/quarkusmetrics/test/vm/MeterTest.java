package example.quarkusmetrics.test.vm;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

@QuarkusTest
public class MeterTest {

    @Test
    public void testCounter() {
        given()
                .when().get("/metrics/application")
                .then()
                .statusCode(200)
                .body(containsString("application:annotated_meter_total 0.0"));
        given()
                .when().get("/meter")
                .then()
                .statusCode(200);
        given()
                .when().get("/metrics/application")
                .then()
                .statusCode(200)
                .body(containsString("application:annotated_meter_total 1.0"));
    }

}
