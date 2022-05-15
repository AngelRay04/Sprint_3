package ru.services.praktikum.qascooter.model;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@AllArgsConstructor
public class CourierCredentials {

    private String login;
    private String password;

     public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }
    public static CourierCredentials onlyLogin(){
        return new CourierCredentials(RandomStringUtils.randomAlphabetic(10),null);
    }
    public static CourierCredentials onlyPassword(){
        return new CourierCredentials(null, RandomStringUtils.randomAlphabetic(10));
    }
}