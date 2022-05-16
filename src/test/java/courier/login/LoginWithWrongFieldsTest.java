package courier.login;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import io.qameta.allure.junit4.DisplayName;
import ru.services.praktikum.qascooter.client.CourierClient;
import ru.services.praktikum.qascooter.model.Courier;
import ru.services.praktikum.qascooter.model.CourierCredentials;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class LoginWithWrongFieldsTest {
    private CourierClient courierClient;
    private int courierId;
    private Courier courier;
    private ValidatableResponse response;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierId = courierClient.createCourierAndGetCourierId(courier);
    }

    @After
    public void tearDown(){
        courierClient.delete(courierId);
    }

    @Test
    @Story("Логин курьера в системе")
    @Description("Логин курьера в системе. Позитивный сценарий")
    @DisplayName("Логин курьера в системе. Позитивный сценарий")
    public void checkNewCourierCanNotLoginWithWrongLogin() {
        courier.setLogin(RandomStringUtils.randomAlphabetic(15));
        actAndAssert();
    }


    @Test
    @Story("Логин курьера в системе")
    @Description("Логин курьера в системе с не верным Password")
    @DisplayName("Логин курьера в системе с не верным Password")
    public void checkNewCourierCanNotLoginWithWrongPassword() {
        courier.setPassword(RandomStringUtils.randomAlphabetic(15));
        actAndAssert();
    }

    public void actAndAssert(){
        response = courierClient.login(CourierCredentials.from(courier));
        response.assertThat().statusCode(SC_NOT_FOUND);
        response.assertThat().extract().path("message").equals("Учетная запись не найдена");
    }
}
