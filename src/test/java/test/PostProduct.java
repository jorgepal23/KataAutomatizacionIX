package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostProduct extends Report {
    @Test (priority = 2)
    public void testPostProductSuccess() throws Exception {
        String body = new String(Files.readAllBytes(Paths.get("src/test/resources/body.json")));
        ExtentTest test = extent.createTest("Post Product Success");
        test.log(Status.INFO, "Beginning test POST Product...");
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("title", equalTo("Nuevo Producto"))
                .body("id", notNullValue()) // Validar que devuelve un ID
                .extract().response();

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "Status Code: " + statusCode);

        Utils.printJsonResponse("POST Response:", response); // 📌 Imprimir con JSON formateado

        String jsonResponse = response.getBody().asPrettyString();
        test.log(Status.INFO,  "POST Response: " + jsonResponse);

        try {
            Assert.assertEquals(response.getStatusCode(), 200, "Wrong status code");
            test.log(Status.PASS, "Successful POST Product");
            Assert.assertNotNull(response.jsonPath().get("id"), "The ID of the created product is null");
            test.log(Status.PASS, "The ID of the created product is not null");
            Assert.assertEquals(response.jsonPath().get("title"), "Nuevo Producto", "The title of the created product is incorrect");
            test.log(Status.PASS, "The title of the created product is correct");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Error en la validacion: " + e.getMessage());
        }
    }
}
