package ru.services.praktikum.qascooter.model;
import com.github.javafaker.Faker;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    static Faker faker = new Faker(new Locale("ru_RU"));

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String deliveryDate;
    private int rentTime;
    private String[] colors;
    private String comment;

    public static Order getRandom() {
        final String firstName = faker.name().firstName();
        final String lastName = faker.name().lastName();
        final String address = faker.address().streetAddress();
        final String phone = faker.phoneNumber().phoneNumber();
        final String metroStation = RandomStringUtils.random(1,"123456789");
        final String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(faker.date().future(23, TimeUnit.DAYS));
        final int rentTime = Integer.parseInt(RandomStringUtils.random(1,"123456789"));
        final String comment = "Комментарий к заказу от " + deliveryDate;
        String[] color = {""};
        return new Order(firstName, lastName, address, metroStation, phone, deliveryDate, rentTime, color, comment);
    }
}