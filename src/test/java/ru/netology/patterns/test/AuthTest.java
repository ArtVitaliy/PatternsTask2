package ru.netology.patterns.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.patterns.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.patterns.data.DataGenerator.Registration.getUser;
import static ru.netology.patterns.data.DataGenerator.getRandomLogin;
import static ru.netology.patterns.data.DataGenerator.getRandomPassword;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginFullRegisteredActiveUserTest() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[role='button'] .button__content").click();
        $("h2").should(Condition.text("Личный кабинет"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[role='button'] .button__content").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);

    }

    @Test
    void userBlockedTest() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[role='button'] .button__content").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[role='button'] .button__content").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    void ShouldWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongLogin);
        $("[role='button'] .button__content").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }
}
