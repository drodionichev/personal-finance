package ru.drodionichev.finance.cli;

import ru.drodionichev.finance.model.User;
import ru.drodionichev.finance.model.Wallet;
import ru.drodionichev.finance.service.AuthService;
import ru.drodionichev.finance.service.FinanceService;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private User currentUser;
    private final FinanceService financeService = new FinanceService();
    private final AuthService authService = new AuthService();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("Здравствуйте! Доступные команды: register, login, income, expense, logout, budget, stats, exit");
        while (true) {
            String command = readLine(">");
            switch (command) {
                case "register" -> register();
                case "login" -> login();
                case "logout" -> logout();
                case "income" -> income();
                case "expense" -> expense();
                case "budget" -> budget();
                case "stats" -> stats();
                case "exit" -> {
                    System.out.println("Выход");
                    return;
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void register() {
        String username = readLine("Логин: ");
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
        String username = readLine("Логин: ");
        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        try {
            currentUser = authService.login(username, password);
            System.out.println("Добро пожаловать, " + username);
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

    private void income() {
        if (!checkLoggedIn()) {
            return;
        }

        try {
            String category = readLine("Введите категорию: ");
            double amount = readNumber("Введите сумму: ");

            currentUser.getWallet().addIncome(category, amount);
            System.out.println("Доход добавлен в размере: " + amount + " в категорию: " + category);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void expense() {
        if (!checkLoggedIn()) {
            return;
        }

        try {
            String category = readLine("Введите категорию: ");
            double amount = readNumber("Введите сумму: ");

            List<String> alerts = financeService.addExpense(currentUser, category, amount);
            System.out.println("Расход добавлен в размере: " + amount + " в категорию: " + category);

            for (String alert : alerts) {
                System.out.println(alert);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void budget() {
        if (!checkLoggedIn()) {
            return;
        }

        try {
            String category = readLine("Введите категорию для бюджета: ");
            double limit = readNumber("Введите лимит для категории: ");

            currentUser.getWallet().setBudget(category, limit);
            System.out.println("Лимит на: " + category + " установлен в размере: " + limit);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void stats() {
        if (!checkLoggedIn()) {
            return;
        }

        Wallet wallet = currentUser.getWallet();
        System.out.println("Общий доход: " + wallet.getTotalIncome());
        System.out.println("Общие расходы: " + wallet.getTotalExpenses());
        System.out.println("Баланс: " + wallet.getBalance());

        if (wallet.getBudgets().isEmpty()) {
            System.out.println("Бюджеты не заданы");
        } else {
            System.out.println("Бюджеты: ");

            for (String category : wallet.getBudgets().keySet()) {
                System.out.println("Категория: " + category + ", Лимит: " + wallet.getBudget(category) + ", Осталось: " + wallet.getRemainingBudget(category));
            }
        }
    }

    private boolean checkLoggedIn () {
        if (currentUser == null) {
            System.out.println("Вы не вошли, введите login");
            return false;
        }
        return true;
    }

    private String readLine (String text){
        System.out.print(text);
        return scanner.nextLine().trim();
    }

    private double readNumber(String text) {
        while (true) {
            String textInput = readLine(text);
            try {
                return Double.parseDouble(textInput);
            } catch (NumberFormatException e) {
                System.out.println("Это не число");
            }
        }
    }

}
