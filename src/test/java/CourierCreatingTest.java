import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourierCreatingTest {
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
    @DisplayName("post courier create")
    @Description("Check creating courier api/v1/courier")
    public void postCourierCreate() {
        Courier courier = Courier.getRandom();
        CourierClient courierClient = new CourierClient();

        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        assertTrue("true", isCreated);
    }

    @Test
    @DisplayName("post courier duplicate")
    @Description("Check creating courier duplicate api/v1/courier")
    public void postCourierDuplicate() {
        Courier courier = Courier.getRandom();

        boolean isCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        String isCreatedDuplicate = courierClient.createDuplicate(courier);

        assertTrue("true", isCreated);
        assertEquals("Этот логин уже используется. Попробуйте другой.", isCreatedDuplicate);
    }

    @Test
    @DisplayName("post courier without one field")
    @Description("Check creating courier without one field api/v1/courier")
    public void postCourierWithoutOneField() {
        Courier courier = Courier.getEmptyPassword();

        String isCreated = courierClient.createWithEmptyField(courier);
        assertEquals("Недостаточно данных для создания учетной записи", isCreated);
    }
}
