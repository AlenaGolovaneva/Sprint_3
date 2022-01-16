import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourierLoginTest {
    public CourierClient courierClient;
    public int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        if(courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("post courier login")
    @Description("Check courier login api/v1/courier/login")
    public void postCourierLogin() {
        Courier courier = Courier.getRandom();
        CourierClient courierClient = new CourierClient();

        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        assertTrue("true", isCreated);
        assertNotNull(courierId);
    }

    @Test
    @DisplayName("post courier login password not correct")
    @Description("Check courier login not correct password api/v1/courier/login")
    public void postCourierLoginPasswordNotCorrect() {
        Courier courier = Courier.getRandom();
        CourierClient courierClient = new CourierClient();
        String courierError;

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        courierError = courierClient.loginDataIsNotCorrect(CourierCredentials.getCourierCredentialsIsNotCorrect(courier));
        assertEquals("Учетная запись не найдена", courierError);
    }

    @Test
    @DisplayName("post courier login is not correct courier")
    @Description("Check courier login if the courier non-existent api/v1/courier/login")
    public void postCourierLoginIsNotCorrectCourier() {
        Courier courier = Courier.getRandom();
        CourierClient courierClient = new CourierClient();
        String courierError;

        courierError = courierClient.loginDataIsNotCorrect(CourierCredentials.getCourierCredentialsIsNotCorrect(courier));
        assertEquals("Учетная запись не найдена", courierError);
    }

    @Test
    @DisplayName("post courier login without password field")
    @Description("Check courier login with password null api/v1/courier/login")
    public void postCourierLoginWithoutPassword() {
        Courier courier = Courier.getRandom();
        CourierClient courierClient = new CourierClient();
        String courierError;

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        courierError = courierClient.loginDataIsNotCorrect(CourierCredentials.getCourierCredentialsPasswordNull(courier));
        assertEquals("Учетная запись не найдена", courierError);
    }
}
