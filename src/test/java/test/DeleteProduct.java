package test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

public class DeleteProduct extends Report {
    @Test
    public void testDeleteProductSuccess() {
        ExtentTest test = extent.createTest("testDeleteProduct");
        test.log(Status.INFO, "Validacion del delete de productos");
        Response response = RestAssured
                .given()
                .when()
                .delete("/1")
                .then()
                .statusCode(200)
                .extract().response();

        Utils.printJsonResponse("DELETE Response:", response); // 📌 Imprimir con JSON formateado

  try {
      Assert.assertEquals(response.getStatusCode(), 200, "Código de respuesta incorrecto");
      Assert.assertFalse(response.asString().isEmpty(), "El cuerpo de la respuesta del DELETE está vacío");
  } catch (AssertionError e) {
      test.log(Status.FAIL, "Error en la validacion: " + e.getMessage());
  }}
}
