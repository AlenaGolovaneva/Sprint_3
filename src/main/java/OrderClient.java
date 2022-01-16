import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {
    private String ORDERS_PATH = "/api/v1/orders";

    @Step
    public ValidatableResponse orderList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS_PATH + "?limit=10&page=0")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Step
    public ValidatableResponse addOrder(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDERS_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(201);

    }

}
