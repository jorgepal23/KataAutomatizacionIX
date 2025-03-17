package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

public class DeleteProduct extends Report {
    @Test(priority = 5)
    public void testDeleteProductSuccess() {
        ExtentTest test = extent.createTest("Delete Product Success");
        test.log(Status.INFO, "Beginning test DELETE Product...");
        Response response = RestAssured
                .given()
                .when()
                .delete("/1")
                .then()
                .statusCode(200)
                .extract().response();

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "Status Code: " + statusCode);
        Utils.printJsonResponse("DELETE Response:", response); // 📌 Imprimir con JSON formateado

        String jsonResponse = response.getBody().asPrettyString();
        test.log(Status.INFO, "DELETE Response: " + jsonResponse);

        try {
            Assert.assertEquals(response.getStatusCode(), 200, "Successful DELETE Product");
            test.log(Status.PASS, "Successful DELETE Product");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Validation error: " + e.getMessage());
        }
    }

    @Test(priority = 6)
    public void testDeleteProductWrong() {
        ExtentTest test = extent.createTest("Delete Product Wrong");
        test.log(Status.INFO, "Beginning test DELETE Product Wrong...");
        Response response = RestAssured
                .given()
                .when()
                .delete("/hola")
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "Status Code: " + statusCode);
        Utils.printJsonResponse("DELETE Invalid Response:", response); // 📌 Imprimir con JSON formateado

        String jsonResponse = response.getBody().asPrettyString();
        test.log(Status.INFO, "DELETE Invalid Response: " + jsonResponse);

        try {
            Assert.assertEquals(response.getStatusCode(), 404, "Successful DELETE Product");
            test.log(Status.PASS, "Successful DELETE Product");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Validation error: " + e.getMessage());
        }
    }
}
