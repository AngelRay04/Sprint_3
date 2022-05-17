package order.accept;

import io.qameta.allure.*;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import io.qameta.allure.junit4.DisplayName;
import ru.services.praktikum.qascooter.client.*;
import ru.services.praktikum.qascooter.model.*;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderAcceptAgainTest {
    private OrderClient orderClient;
    private CourierClient courierClient;
    private Order order;
    private Courier courier;
    private int orderId;
    private int courierId;
    private int orderTrack;
    private ValidatableResponse responseOrderAcceptGetFirstTime;
    private ValidatableResponse responseAccept;


    @Before
    public void setUp(){
        orderClient = new OrderClient();
        courierClient =  new CourierClient();
        order = Order.getRandom();
        courier = Courier.getRandom();
        orderTrack = orderClient.create(order).extract().path("track");
        orderId = orderClient.getOne(orderTrack).extract().path("order.id");
        courierId = courierClient.createCourierAndGetCourierId(courier);
        orderClient.acceptOrder(orderId, courierId);
    }
    @After
    public void tearDown(){
        orderClient.finish(orderId);
    }
    @Test
    @Story("Принять заказ")
    @Description("Попытка принять заказ повторно")
    @DisplayName("Попытка принять заказ повторно")
    public void checkOrderCanBeAcceptedByCourier(){
        //Arrange
        responseOrderAcceptGetFirstTime = orderClient.getOne(orderTrack);

        //Act
        responseAccept = orderClient.acceptOrder(orderId, courierId);

        //Assert
        assertThat(responseOrderAcceptGetFirstTime.extract().path("order.status"), equalTo(1));
        responseAccept.assertThat().statusCode(SC_CONFLICT);
        assertThat(responseAccept.extract().path("message"), equalTo("Этот заказ уже в работе"));
    }
}