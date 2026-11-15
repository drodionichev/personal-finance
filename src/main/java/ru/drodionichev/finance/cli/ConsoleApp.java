package ru.drodionichev.finance.cli;

import ru.drodionichev.finance.model.User;
import ru.drodionichev.finance.service.AuthService;
import ru.drodionichev.finance.service.FinanceService;

import java.util.Scanner;

public class ConsoleApp {
    private User currentUser;
    private final FinanceService financeService = new FinanceService();
    private final AuthService authService = new AuthService();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("Здравствуйте! Доступные команды: register, login, income, logout, exit");
        while (true) {
            System.out.print(">");

            String command = scanner.nextLine();
            switch (command) {
                case "register" -> register();
                case "login" -> login();
                case "logout" -> logout();
                case "income" -> income();
                case "exit" -> {
                    System.out.println("Выход");
                    return;
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void register() {
        System.out.print("Логин: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        try {
            authService.register(username, password);
            System.out.println("Пользователь создан");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void login() {
        System.out.print("Логин: ");
        String username = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        try {
            authService.login(username, password);
            System.out.println("Добро пожаловать, " + username);
            currentUser = authService.login(username, password);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void logout() {
        if (!checkLoggedIn()) {
            return;
        }
        currentUser = null;
        System.out.println("Вы вышли");
    }

    private boolean checkLoggedIn() {
        if (currentUser == null) {
            System.out.println("Вы не вошли, введите login");
            return false;
        }
        return true;
    }

    private void income() {
        if (!checkLoggedIn()) {
            return;
        }

        try {
            System.out.print("Введите категорию: ");
            String category = scanner.nextLine();
            System.out.print("Введите сумму: ");
            String amountText = scanner.nextLine();
            double amount = Double.parseDouble(amountText);

            currentUser.getWallet().addIncome(category, amount);
            System.out.println("Доход добавлен в размере: " + amount + " в категорию: " + category);
        } catch (NumberFormatException e){
            System.out.println("Сумма должна быть числом");
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

}
