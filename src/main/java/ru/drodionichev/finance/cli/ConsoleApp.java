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
        while (true) {
            System.out.print(">");
            String command = scanner.nextLine();
            switch (command) {
                case "exit" -> {
                    System.out.println("Выход");
                    return;
                }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }
}
