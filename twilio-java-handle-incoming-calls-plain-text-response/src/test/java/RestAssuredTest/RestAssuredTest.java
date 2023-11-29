package RestAssuredTest;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTest {

        @BeforeClass
        public static void setup() {
            RestAssured.baseURI = "http://localhost:8082";
        }

        @Test
        public void testHandleIncomingCall() {
            RestAssured.given()
                    .when()
                    .post("/")
                    .then()
                    .statusCode(200)
                    .and()
                    .body("Response.Gather.Say[0]", equalTo("Hello. Press or say One for a joke, Two for some music, or Three for information about our products."))
                    .and()
                    .body("Response.Say[0]", equalTo("Thanks for calling, have a great day"));
        }

        @Test
        public void testHandleGatherResultWithDigitOne() {
            RestAssured.given()
                    .param("Digits", "1")
                    .when()
                    .post("/gatherResult")
                    .then()
                    .statusCode(200)
                    .and()
                    .body("Response.Say[0]", equalTo("How do you know if a bee is using your phone? The line will be buzzy."));
        }

        @Test
        public void testHandleGatherResultWithDigitTwo() {
            RestAssured.given()
                    .param("Digits", "2")
                    .when()
                    .post("/gatherResult")
                    .then()
                    .statusCode(200)
                    .and()
                    .body("Response.Play[0]", equalTo("http://demo.twilio.com/docs/classic.mp3"));
        }
        @Test
        public void testHandleGatherResultWithDigitThree() {
            RestAssured.given()
                    .param("Digits", "3")
                    .when()
                    .post("/gatherResult")
                    .then()
                    .statusCode(200)
                    .and()
                    .body("Response.Say[0]", equalTo("Thank you for your interest in our products. Here's some information..."));
        }
    }

