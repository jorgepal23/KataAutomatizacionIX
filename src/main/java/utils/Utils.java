package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.restassured.response.Response;

public class Utils {
    public static void printJsonResponse(String message, Response response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            String prettyJson = writer.writeValueAsString(mapper.readTree(response.asString()));

            System.out.println("📌 " + message);
            System.out.println(prettyJson);
        } catch (Exception e) {
            System.out.println("❌ Error al formatear JSON: " + e.getMessage());
            System.out.println(response.asString());
        }
    }
}
