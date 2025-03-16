package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utilities.Constantes.BASE_URL;

public class PostProduct {
    @Test
    public void testPostProductSuccess() throws Exception {
        String body = new String(Files.readAllBytes(Paths.get("src/test/resources/body.json")));

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

        // Asserts adicionales
        Assert.assertEquals(response.getStatusCode(), 200, "Código de respuesta incorrecto");
        Assert.assertNotNull(response.jsonPath().get("id"), "El ID del producto creado es nulo");
        Assert.assertEquals(response.jsonPath().get("title"), "Nuevo Producto", "El título del producto no coincide");
    }
}
