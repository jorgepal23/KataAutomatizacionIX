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
    @Test
    public void testPostProductSuccess() throws Exception {
        String body = new String(Files.readAllBytes(Paths.get("src/test/resources/body.json")));
        ExtentTest test = extent.createTest("testPostProduct");
        test.log(Status.INFO, "Validacion del POST de productos");
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

        Utils.printJsonResponse("POST Response:", response); // 📌 Imprimir con JSON formateado

        try {
            Assert.assertEquals(response.getStatusCode(), 200, "Código de respuesta incorrecto");
            Assert.assertNotNull(response.jsonPath().get("id"), "El ID del producto creado es nulo");
            Assert.assertEquals(response.jsonPath().get("title"), "Nuevo Producto", "El título del producto no coincide");
        } catch (AssertionError e) {
            test.log(Status.FAIL, "Error en la validacion: " + e.getMessage());
        }
    }
}
