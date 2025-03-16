package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

public class GetProduct extends Report {
    @Test
    public void testGetProductsSuccess() {
        ExtentTest test = extent.createTest("testGetProductsSuccess");
        test.log(Status.INFO, "Validacion del get de productos");
        Response response = RestAssured
                .given()
                .when()
                .get("/2")
                .then()
                .statusCode(200)
                .extract().response();
        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "Código de respuesta: " + statusCode);
        Utils.printJsonResponse("GET Response:", response); // 📌 Usamos Utils

        try {
            Assert.assertEquals(statusCode, 200, "Código de respuesta correcta");
            test.log(Status.PASS, "Código de respuesta correcta");

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Error en la validacion: " + e.getMessage());
        }
    }
}
