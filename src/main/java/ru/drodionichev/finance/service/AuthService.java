package ru.drodionichev.finance.service;

import ru.drodionichev.finance.model.User;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, User> users;

    public AuthService(Map<String, User> users){
        this.users = users;
    }

    public Map<String, User> getUsers(){
        return users;
    }

    public User register(String login, String password){
        if(users.containsKey(login)){
            throw new IllegalArgumentException("Логин уже занят");
        }
            User user = new User(login, password);
            users.put(login, user);
        return user;
    }

    public User login(String login, String password){
        User user = users.get(login);
        if(user == null || !user.checkPassword(password)){
            throw new IllegalArgumentException("Неверный логин или пароль");
        }
        return user;
    }
}
