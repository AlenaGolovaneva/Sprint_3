import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreatingTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("post courier create")
    @Description("Check creating courier api/v1/courier")
    public void postCourierCreate() {
        String login = "AlenaTestLogin";
        String password = "1234";

        Response response = null;
        try {
            String registerRequestBody = "{\"login\":\"" + login + "\","
                    + "\"password\":\"" + password + "\","
                    + "\"firstName\":\"" + "testFristName" + "\"}";

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier");
            response.then().assertThat().body("ok", equalTo(true))
                    .and()
                    .statusCode(201);
        } finally {
            if (response.statusCode() == 201){
                deleteCourier(login, password);
            }        }

    }

    @Test
    @DisplayName("post courier duplicate")
    @Description("Check creating courier duplicate api/v1/courier")
    public void postCourierDuplicate() {
        String login = "AlenaTestLogin";
        String password = "1234";

        Response response = null;
        try {
            String registerRequestBody = "{\"login\":\"" + login + "\","
                    + "\"password\":\"" + password + "\","
                    + "\"firstName\":\"" + "testFristName" + "\"}";

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier");
            response.then().assertThat().body("ok", equalTo(true))
                    .and()
                    .statusCode(201);

            Response responseDuplicate = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier");
            responseDuplicate.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                    .and()
                    .statusCode(409);
        } finally {
            if (response.statusCode() == 201){
                deleteCourier(login, password);
            }
        }

    }

    @Test
    @DisplayName("post courier without one field")
    @Description("Check creating courier without one field api/v1/courier")
    public void postCourierWithoutOneField() {
        String login = "AlenaTestLogin";
        String password = "";

        Response response = null;
        try {

            String registerRequestBody = "{\"login\":\"" + login + "\","
                    + "\"password\":\"" + password + "\","
                    + "\"firstName\":\"" + "testFristName" + "\"}";

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier");
            response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                    .and()
                    .statusCode(400);
        }finally {
            if (response.statusCode() == 201){
                deleteCourier(login, password);
            }        }
    }

    @Step("deleting courier ")
    public void deleteCourier(String login, String password) {
        String loginRequestBody = "{\"login\":\"" + login + "\","
                + "\"password\":\"" + password + "\"}";

        Response response2 = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginRequestBody)
                .when()
                .post("/api/v1/courier/login");

        response2.then().statusCode(200);

        int id = response2.path("id");
        String idString = Integer.toString(id);

        String deleteRequestBody = "{\"id\":\"" + id + "\"}";

        Response response3 = given()
                .header("Content-type", "application/json")
                .and()
                .body(deleteRequestBody)
                .when()
                .delete("/api/v1/courier/" + idString);

        response3.then().statusCode(200);
    }
}
