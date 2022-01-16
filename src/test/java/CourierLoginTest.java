import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("post courier login")
    @Description("Check courier login api/v1/courier/login")
    public void postCourierLogin() {
        ArrayList<String> loginPass = registerNewCourierAndReturnLoginPassword();
        Response response = null;

        try {
            String registerRequestBody = "{\"login\":\"" + loginPass.get(0) + "\","
                    + "\"password\":\"" + loginPass.get(1) + "\"}";

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier/login");
            response.then().assertThat().body("id", notNullValue())
                    .and()
                    .statusCode(200);

        } finally {
            if (response.statusCode() == 200) {
                int id = response.path("id");
                deleteCourier(id);
            }
        }

    }

    @Test
    @DisplayName("post courier login password not correct")
    @Description("Check courier login not correct password api/v1/courier/login")
    public void postCourierLoginPasswordNotCorrect() {
        ArrayList<String> loginPass = registerNewCourierAndReturnLoginPassword();
        Response response = null;

        try {

            String registerRequestBody = "{\"login\":\"" + loginPass.get(0) + "\","
                    + "\"password\":\"" + "1111" + "\"}";

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier/login");
            response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                    .and()
                    .statusCode(404);

        } finally {

                String registerRequestBody = "{\"login\":\"" + loginPass.get(0) + "\","
                        + "\"password\":\"" + loginPass.get(1) + "\"}";

                response = given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(registerRequestBody)
                        .when()
                        .post("/api/v1/courier/login");
                response.then().assertThat().body("id", notNullValue())
                        .and()
                        .statusCode(200);

                int id = response.path("id");
                deleteCourier(id);

        }

    }

    @Test
    @DisplayName("post courier login is not correct courier")
    @Description("Check courier login if the courier non-existent api/v1/courier/login")
    public void postCourierLoginIsNotCorrectCourier() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

            String registerRequestBody = "{\"login\":\"" + login + "\","
                    + "\"password\":\"" + password + "\"}";

        Response response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier/login");
            response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                    .and()
                    .statusCode(404);
    }

    @Test
    @DisplayName("post courier login without password field")
    @Description("Check courier login with out password field api/v1/courier/login")
    public void postCourierLoginWithoutPasswordField() {
        ArrayList<String> loginPass = registerNewCourierAndReturnLoginPassword();
        Response response = null;

        try {

            String registerRequestBody = "{\"login\":\"" + loginPass.get(0) + "\"}";

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier/login");
            response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                    .and()
                    .statusCode(400);

        } finally {

            String registerRequestBody = "{\"login\":\"" + loginPass.get(0) + "\","
                    + "\"password\":\"" + loginPass.get(1) + "\"}";

            response = given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(registerRequestBody)
                    .when()
                    .post("/api/v1/courier/login");
            response.then().assertThat().body("id", notNullValue())
                    .and()
                    .statusCode(200);

            int id = response.path("id");
            deleteCourier(id);

        }

    }

    @Step("adding courier ")
    public ArrayList<String> registerNewCourierAndReturnLoginPassword(){

        String courierLogin = RandomStringUtils.randomAlphabetic(10);
        String courierPassword = RandomStringUtils.randomAlphabetic(10);
        String courierFirstName = RandomStringUtils.randomAlphabetic(10);

        // создаём список, чтобы метод мог его вернуть
        ArrayList<String> loginPass = new ArrayList<>();

        String registerRequestBody = "{\"login\":\"" + courierLogin + "\","
                + "\"password\":\"" + courierPassword + "\","
                + "\"firstName\":\"" + courierFirstName + "\"}";

        Response response =  given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier");

        if (response.statusCode() == 201) {
            loginPass.add(courierLogin);
            loginPass.add(courierPassword);
        }

        return loginPass;
    }

    @Step("deleting courier ")
    public void deleteCourier(int id) {

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
