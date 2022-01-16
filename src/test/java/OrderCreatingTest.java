import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreatingTest {
    private final Order order;

    public OrderCreatingTest(Order order) {

        this.order = order;
    }

    @Parameterized.Parameters
    public static Object[][] getBody() {
        return new Object[][]{
                {Order.getOrderDataWithTwoColors()},
                {Order.getOrderDataColorBlack()},
                {Order.getOrderDataWithOutColor()},
        };
    }

    @Test
    @DisplayName("post order creating")
    @Description("Check creating orders api/v1/orders")
    public void postOrderCreating() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.addOrder(order);

        response.assertThat().body("track", notNullValue());

    }
}
