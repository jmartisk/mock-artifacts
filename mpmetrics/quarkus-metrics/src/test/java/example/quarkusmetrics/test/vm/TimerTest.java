package example.quarkusmetrics.test.vm;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringContains.containsString;

@QuarkusTest
public class TimerTest {

    @Test
    public void testReusableTimer() {
        given()
                .when().get("/metrics/application")
                .then()
                .statusCode(200)
                .body(containsString("application:annotated_timer_seconds_count 0.0"));
        given()
                .when().get("/timer")
                .then()
                .statusCode(200);
        given()
                .when().get("/timer2")
                .then()
                .statusCode(200);
        given()
                .when().get("/metrics/application")
                .then()
                .statusCode(200)
                .body(containsString("application:annotated_timer_seconds_count 2.0"));
    }

}
