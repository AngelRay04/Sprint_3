package ru.services.praktikum.qascooter.model;

import com.github.javafaker.Faker;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courier {
    static Faker faker = new Faker(new Locale("ru_RU"));

    private String login;
    private String password;
    private String firstName;

    public static Courier getRandom() {
        final String login = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String firstName = faker.name().firstName();
        return new Courier(login, password, firstName);
    }
    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }
    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }
    public static Courier setRandomLogin() {

        return new Courier().setLogin(RandomStringUtils.randomAlphabetic(10));
    }
    public static Courier setRandomPassword() {
        return new Courier().setPassword(RandomStringUtils.randomAlphabetic(10));
    }
}
