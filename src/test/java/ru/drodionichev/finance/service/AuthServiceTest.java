package ru.drodionichev.finance.service;

import org.junit.jupiter.api.Test;
import ru.drodionichev.finance.model.User;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Test
    void registerAddsUserToUsers () {
        AuthService authService = new AuthService(new HashMap<>());

        authService.register("Sith","1234");

        assertTrue(authService.getUsers().containsKey("Sith"));
    }

    @Test
    void loginReturnsRegisteredUser(){
        AuthService authService = new AuthService(new HashMap<>());
        authService.register("Sith","1234");

        User user = authService.login("Sith", "1234");
        assertEquals("Sith", user.getLogin());
    }

    @Test
    void duplicateLoginIsRejected(){
        AuthService authService = new AuthService(new HashMap<>());

        authService.register("Sith","1234");

        assertThrows(IllegalArgumentException.class,
                () -> authService.register("Sith","1234"));
    }

    @Test
    void loginWithUnknownLoginThrows(){
        AuthService authService = new AuthService(new HashMap<>());

        assertThrows(IllegalArgumentException.class,
                () -> authService.login("Sith","1234"));
    }

    @Test
    void loginWithWrongPasswordThrows(){
        AuthService authService = new AuthService(new HashMap<>());

        authService.register("Sith","1234");

        assertThrows(IllegalArgumentException.class,
                () -> authService.login("Sith","0000"));
    }
}