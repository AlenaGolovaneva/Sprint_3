import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("get order list")
    @Description("Check orders list api/v1/orders?limit=10&page=0")
    public void getOrderList() {
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders?limit=10&page=0");
        response.then().assertThat().body("orders[0].id", notNullValue())
                .and()
                .statusCode(200);
    }
}
