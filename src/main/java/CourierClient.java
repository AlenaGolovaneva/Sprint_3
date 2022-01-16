import io.qameta.allure.Step;

import static io.restassured.RestAssured.*;

public class CourierClient extends RestAssuredClient {

    private String COURIER_PATH = "/api/v1/courier/";

    @Step
    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    @Step
    public String createDuplicate(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");
    }

    @Step
    public String createWithEmptyField(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step
    public int login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step
    public String loginDataIsNotCorrect(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step
    public boolean delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

}
