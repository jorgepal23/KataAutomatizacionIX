package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

public class DeleteProduct {
    @Test
    public void testDeleteProductSuccess() {
        Response response = RestAssured
                .given()
                .when()
                .delete("/1")
                .then()
                .statusCode(200)
                .extract().response();

        Utils.printJsonResponse("DELETE Response:", response); // 📌 Imprimir con JSON formateado

        // Asserts adicionales
        Assert.assertEquals(response.getStatusCode(), 200, "Código de respuesta incorrecto");
        Assert.assertFalse(response.asString().isEmpty(), "El cuerpo de la respuesta del DELETE está vacío");
    }

}
