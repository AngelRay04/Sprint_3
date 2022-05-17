package courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import ru.services.praktikum.qascooter.client.CourierClient;
import ru.services.praktikum.qascooter.model.Courier;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class CreateSameCourierTest {
    private CourierClient courierClient;
    private int courierId;
    private Courier courierFirst;
    private Courier courierSecond;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courierFirst = Courier.getRandom();
        courierClient.create(courierFirst);
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @Story("Создание курьера")
    @Description("Создание курьера с логином, который уже используется")
    @DisplayName("Создание курьера {courier} с логином, который уже используется")
    public void checkSameCourierCanNotBeCreatedSecondTime() {
        //Arrange
        courierSecond = Courier.getRandom();
        courierSecond.setLogin(courierFirst.getLogin());

        //Act
        response = courierClient.create(courierSecond);
        courierId = courierClient.getCourierId(courierFirst);

        //Assert
        response.assertThat().statusCode(SC_CONFLICT);
        response.assertThat().extract().path("message").equals("Этот логин уже используется.");
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }
}
