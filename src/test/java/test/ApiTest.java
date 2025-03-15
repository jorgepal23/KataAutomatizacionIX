package test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTest {

    private static final String BASE_URL = "https://fakestoreapi.com/products";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test(priority = 1)
    public void testGetProductsSuccess() {
        Response response = RestAssured
                .given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("[0].id", notNullValue()) // Validar que hay productos
                .extract().response();

        System.out.println("GET Response: " + response.asString());
    }

    @Test(priority = 2)
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
                .extract().response();

        System.out.println("POST Response: " + response.asString());
    }

    @Test(priority = 3)
    public void testPutProductSuccess() throws Exception {
        String body = new String(Files.readAllBytes(Paths.get("src/test/resources/body.json")));

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

        System.out.println("PUT Response: " + response.asString());
    }

    @Test(priority = 4)
    public void testDeleteProductSuccess() {
        Response response = RestAssured
                .given()
                .when()
                .delete("/1")
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("DELETE Response: " + response.asString());
    }

    @Test(priority = 5)
    public void testGetInvalidProduct() {
        Response response = RestAssured
                .given()
                .when()
                .get("/99999") // Producto que no existe
                .then()
                .statusCode(200) // Aceptar 200 en lugar de 404
                .extract().response();

        // Validar que la respuesta está vacía o contiene un mensaje de error
        String responseBody = response.asString();
        System.out.println("GET Invalid Product Response: " + responseBody);

        // Si la API devuelve un JSON vacío, validar eso
        Assert.assertTrue(responseBody.isEmpty() || responseBody.equals("{}"),
                "La respuesta no es la esperada para un producto inexistente");
    }

}
