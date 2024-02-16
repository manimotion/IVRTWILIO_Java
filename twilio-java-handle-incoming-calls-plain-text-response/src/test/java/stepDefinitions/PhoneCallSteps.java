package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.containsString;

public class PhoneCallSteps {

    private Response response;

    @Given("the ivr system is operational")
    public void givenTheServerIsRunning() {
        RestAssured.baseURI = "https://jl3xjd66-8082.use2.devtunnels.ms/";
    }

    @When("a call is made")
    public void whenACallIsMade() {
        response = RestAssured.given().when().post("/");
    }

    @When("a call is made with digit {string}")
    public void whenACallIsMadeWithDigit(String digit) {
        response = RestAssured.given().param("Digits", digit).when().post("/gatherResult");
    }

    @Then("the response should contain {string}")
    public void thenTheResponseShouldContain(String expectedContent) {
        response.then().statusCode(200).and().body(containsString(expectedContent));
    }

    @When("a call is made with digit three and submenu digit {string}")
    public void aCallIsMadeWithSubmenuDigit(String submenuDigit) {
        response = RestAssured.given().param("Digits", submenuDigit).when().post("/productSubMenu");
    }

    @Given("use an invalid submenu option like {int}")
    public void use_an_invalid_submenu_option_like(Integer int1) {
        response = RestAssured.given().param("Digits", int1).when().post("/productSubMenu");
    }
}
