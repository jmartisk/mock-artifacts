package example.quarkusmetrics.test.vm;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CounterTest {

    @Test
    public void testCounter() {
        given()
                .when().get("/")
                .then()
                .statusCode(200)
                .body(is("1"));
        given()
                .when().get("/")
                .then()
                .statusCode(200)
                .body(is("2"));
    }


}
