package example.quarkusmetrics.test.vm;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

@QuarkusTest
public class InjectTest {

    @Test
    public void testCounterInject() {
        // TODO: eager init for @Inject-ed metrics not implemented yet
//        given()
//                .when().get("/metrics")
//                .then()
//                .statusCode(200)
//                .body(containsString("application:injected_counter 0.0"));
        given()
                .when().get("/inject/counter")
                .then()
                .statusCode(200);
        given()
                .when().get("/metrics/application")
                .then()
                .statusCode(200)
                .body(containsString("application:injected_counter 1.0"));
    }

}
