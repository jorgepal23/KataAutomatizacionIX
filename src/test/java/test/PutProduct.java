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

public class PutProduct extends Report {
    @Test(priority = 3)
    public void testPutProductSuccess() throws Exception {
        String body = new String(Files.readAllBytes(Paths.get("src/test/resources/body.json")));
        ExtentTest test = extent.createTest("PUT Product Success");
        test.log(Status.INFO, "Beginning test PUT Product...");

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/1") // Modificar producto con ID 1
                .then()
                .statusCode(200)
                .body("title", equalTo("Nuevo Producto"))
                .extract().response();

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "Status Code: " + statusCode);

        Utils.printJsonResponse("PUT Response:", response); // 📌 Imprimir con JSON formateado

        String jsonResponse = response.getBody().asPrettyString();
        test.log(Status.INFO, "PUT Response: " + jsonResponse);

        try {
            Assert.assertNotNull(response.jsonPath().get("id"), "The ID of the updated product is null");
            test.log(Status.INFO, "The ID of the updated product is not null");
            Assert.assertEquals(response.jsonPath().get("title"), "Nuevo Producto", "The title of the product was not updated successfully");
            test.log(Status.INFO, "The title of the product was updated successfully");
            Assert.assertEquals(response.getStatusCode(), 200, "Successful PUT Product");
            test.log(Status.PASS, "Successful PUT Product");

        } catch (AssertionError e) {
            test.log(Status.FAIL, "Validation error: " + e.getMessage());
        }
    }

    @Test(priority = 4)
    public void testPutProductWrong() throws Exception {
        // Leer el JSON del archivo
        String body = new String(Files.readAllBytes(Paths.get("src/test/resources/body.json")));
        ExtentTest test = extent.createTest("PUT Product Wrong");
        test.log(Status.INFO, "Beginning test PUT Product Wrong...");

        // Enviar la solicitud con un endpoint inválido
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put("/hola") // Endpoint incorrecto
                .then()
                .extract().response();

        int statusCode = response.getStatusCode();
        test.log(Status.INFO, "Status Code: " + statusCode);

        // Imprimir la respuesta formateada
        Utils.printJsonResponse("PUT Invalid Response:", response);

        String jsonResponse = response.getBody().asPrettyString();
        test.log(Status.INFO, "PUT Invalid Response: " + jsonResponse);

        try {
            // Validar que el código de respuesta sea 400 o 404, dependiendo de la API
            Assert.assertTrue(statusCode == 400 || statusCode == 404,
                    "❌ Code to : " + statusCode);
            Assert.assertNotNull(response.jsonPath().get("id"), "The ID of the updated product is null");
            test.log(Status.INFO, "The ID of the updated product is not null");
            Assert.assertEquals(response.jsonPath().get("title"), "Nuevo Producto", "The title of the product was not updated successfully");
            test.log(Status.INFO, "The title of the product was updated successfully");
            Assert.assertEquals(response.getStatusCode(), 200, "Successful PUT Product");
            test.log(Status.PASS, "Successful PUT Product");

        } catch (AssertionError e) {
            // Si la API devuelve un mensaje de error, verificarlo
            if (response.jsonPath().get("message") != null) {
                test.log(Status.INFO, "Message received: " + response.jsonPath().get("message"));
                Assert.assertFalse(response.jsonPath().get("message").toString().isEmpty(),
                        "The error message is empty");
                test.log(Status.INFO, "The error message is not empty");
            }
            test.log(Status.FAIL, "Validation error: " + e.getMessage());
        }
    }

}
