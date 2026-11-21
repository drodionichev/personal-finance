package ru.drodionichev.finance.cli;

import ru.drodionichev.finance.model.User;
import ru.drodionichev.finance.model.Wallet;
import ru.drodionichev.finance.service.AuthService;
import ru.drodionichev.finance.service.FinanceService;
import ru.drodionichev.finance.storage.FileStorage;
import ru.drodionichev.finance.storage.Storage;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApp {
    private User currentUser;
    private final Storage storage = new FileStorage();
    private final FinanceService financeService = new FinanceService();
    private final AuthService authService = new AuthService(storage.load());
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("Здравствуйте, введите help, чтобы ознакомиться с командами сервиса");
        while (true) {
            String command = readLine(">");
            switch (command) {
                case "help" -> help();
                case "register" -> register();
                case "login" -> login();
                case "logout" -> logout();
                case "income" -> income();
                case "expense" -> expense();
                case "budget" -> budget();
                case "stats" -> stats();
                case "exit" -> {
                    save();
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
            save();
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
            save();
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
            save();
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
            save();
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

        Map<String, Double> incomes = wallet.getIncomeTotalsByCategory();
        Map<String, Double> expense = wallet.getExpenseTotalsByCategory();

        if (incomes.isEmpty()){
            System.out.println("Доходов пока нет");
        } else {
            System.out.println("Доходы по категориям: ");

            for (String category : incomes.keySet()) {
                System.out.println("  " + category + ": " + incomes.get(category));
            }
        }

        if (expense.isEmpty()){
            System.out.println("Расходов пока нет");
        } else {
            System.out.println("Расходы по категориям: ");

            for (String category : expense.keySet()) {
                System.out.println("  " + category + ": " + expense.get(category));
            }
        }

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

    private void save(){
        storage.save(authService.getUsers());
    }

    private void help() {
        System.out.println("register - регистрация по логину и паролю");
        System.out.println("login - вход в аккаунт по логину и паролю");
        System.out.println("logout - выход из аккаунта");
        System.out.println("income - добавить доход в категорию");
        System.out.println("expense - добавить расход в категорию");
        System.out.println("budget - установить лимит на категорию");
        System.out.println("stats - отобразить общий доход, расходы, баланс, остаток по каждому бюджету, доходы и расходы по категориям");
        System.out.println("exit - сохранить данные и выйти");
        System.out.println("help - показать эту справку");
    }
}
