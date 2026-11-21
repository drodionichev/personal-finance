package ru.drodionichev.finance.storage;

import ru.drodionichev.finance.model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileStorage implements Storage{
    private static final String FILE_NAME = "finance.dat";

    @Override
    public void save(Map<String, User> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(users);
        } catch (IOException e) {
            System.out.println("Не удалось сохранить данные");
        }
    }

    @Override
    public Map<String, User> load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Map<String, User>) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            return new HashMap<>();
        }
    }
}
