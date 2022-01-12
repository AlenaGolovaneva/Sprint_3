import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Description;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreatingTest {
    private final File bodyData;
    private final static File jsonColorBlack = new File("src/test/java/resources/orderDataColorBlack.json");
    private final static File jsonWithOutColor = new File("src/test/java/resources/orderDataWithOutColor.json");
    private final static File jsonWithTwoColors = new File("src/test/java/resources/orderDataWithTwoColors.json");

    public OrderCreatingTest(File bodyData) {
        this.bodyData = bodyData;
    }

    @Parameterized.Parameters
    public static File[] getBody() {
        return new File[]{
                jsonColorBlack,
                jsonWithOutColor,
                jsonWithTwoColors
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("post order creating")
    @Description("Check creating orders api/v1/orders")
    public void postOrderCreating() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(bodyData)
                .when()
                .post("/api/v1/orders");
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}
