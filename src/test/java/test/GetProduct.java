package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

public class GetProduct extends Report {
    @Test (priority = 1)
    public void testGetProductsSuccess() {
        ExtentTest test = extent.createTest("Get Products Success");
        test.log(Status.INFO, "Beginning test GET Product...");
        Response response = RestAssured
                .given()
                .when()
                .get("/2")
                .then()
                .statusCode(200)
                .extract().response();
        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "Status CodeS: " + statusCode);
        Utils.printJsonResponse("GET Response:", response); // 📌 Usamos Utils

        String jsonResponse = response.getBody().asPrettyString();
        test.log(Status.INFO,  "GET Response: " + jsonResponse);

        try {
            Assert.assertEquals(statusCode, 200, "Successful GET Product");
            test.log(Status.PASS, "Successful GET Product");

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Validation error: " + e.getMessage());
        }
    }
}
