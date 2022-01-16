import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;


public class OrderListTest {

    @Test
    @DisplayName("get order list")
    @Description("Check orders list api/v1/orders?limit=10&page=0")
    public void getOrderList() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse order;

        order = orderClient.orderList();

        order.assertThat().body("orders[0].id", notNullValue());

    }
}
