package test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static utilities.Constantes.BASE_URL;

public class Report {
    protected static ExtentReports extent;

    @BeforeSuite
    public void beforeSuite() {
        String path = "target/reports/pruebas.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        extent = new ExtentReports();
        extent.attachReporter(reporter);
    }

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @AfterSuite
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
    }
}
