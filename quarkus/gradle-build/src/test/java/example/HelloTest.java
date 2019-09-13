package example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class HelloTest {

    @Test
    public void test() {
        RestAssured
                .when()
                    .get("/hello")
                .then()
                    .body(containsString("Hello"))
                    .statusCode(200);

    }

}
