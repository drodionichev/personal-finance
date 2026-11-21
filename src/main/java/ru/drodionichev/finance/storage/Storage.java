package ru.drodionichev.finance.storage;

import ru.drodionichev.finance.model.User;

import java.util.Map;

public interface Storage {
    void save(Map<String, User> users);
    Map<String, User> load();
}
