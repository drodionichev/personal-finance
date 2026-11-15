package ru.drodionichev.finance.model;

import java.io.Serializable;

public class User implements Serializable {
    private final String login;
    private String password;
    private final Wallet wallet;

    public User(String login, String password) {

        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Логин не может быть пустым");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Пароль не может быть пустым");
        }

        this.login = login;
        this.password = password;
        this.wallet = new Wallet();
    }

    public String getLogin() {
        return login;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public boolean checkPassword(String attempt) {
        return password.equals(attempt);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return login.equals(user.login);
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}

