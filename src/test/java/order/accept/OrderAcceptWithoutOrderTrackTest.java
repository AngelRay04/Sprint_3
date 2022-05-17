package order.accept;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import io.qameta.allure.junit4.DisplayName;
import ru.services.praktikum.qascooter.client.*;
import ru.services.praktikum.qascooter.model.*;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderAcceptWithoutOrderTrackTest {
    private OrderClient orderClient;
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;
    private ValidatableResponse responseAccept;


    @Before
    public void setUp(){
        orderClient = new OrderClient();
        courierClient =  new CourierClient();
        courier = Courier.getRandom();
    }

    @Test
    @Story("Принять заказ")
    @Description("Попытка принять заказ. В запросе не указан трек Id")
    @DisplayName("Попытка принять заказ. В запросе не указан трек Id")
    public void checkOrderCanNotBeAcceptedWithoutOrderTrack(){
        //Arrange
        courierId = courierClient.createCourierAndGetCourierId(courier);

        //Act
        responseAccept = orderClient.acceptOrder(courierId);

        //Assert
        responseAccept.assertThat().statusCode(SC_BAD_REQUEST);
        assertThat(responseAccept.extract().path("message"), equalTo("Недостаточно данных для поиска"));
            }
}