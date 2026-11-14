package ru.drodionichev.finance.cli;

import ru.drodionichev.finance.model.User;
import ru.drodionichev.finance.model.Wallet;
import ru.drodionichev.finance.service.AuthService;
import ru.drodionichev.finance.service.FinanceService;

public class Main {
        public static void main(String[] args) {
            Wallet wallet = new Wallet();
            wallet.addIncome("Зарплата", 20000);
            wallet.addIncome("Зарплата", 40000);
            wallet.addIncome("Бонус", 3000);

            wallet.addExpense("Еда", 300);
            wallet.addExpense("Еда", 500);
            wallet.addExpense("Развлечения", 3000);
            wallet.addExpense("Коммунальные услуги", 3000);
            wallet.addExpense("Такси", 1500);

//            System.out.println(wallet.getBalance());

            wallet.setBudget("Еда", 4000);
            wallet.setBudget("Развлечения", 3000);
            wallet.setBudget("Коммунальные услуги", 2500);

//            System.out.println(wallet.getBudget("Еда"));
//            wallet.hasBudget("Кот");
//            wallet.getBudget("Кот");

            System.out.println(wallet.getRemainingBudget("Еда"));
            System.out.println(wallet.getRemainingBudget("Развлечения"));
            System.out.println(wallet.getRemainingBudget("Коммунальные услуги"));

            AuthService authService = new AuthService();
            authService.register("Sith", "1234");

            User user1 = authService.login("Sith","1234");
            Wallet wallet1 = user1.getWallet();
            System.out.println(wallet1.getBalance()); // 0
            wallet1.addIncome("Зарплата", 4000);
            System.out.println(wallet1.getBalance()); // 4000

            // негативные тесты
//          authService.register("Sith", "1234");
//          User user2 = authService.login("Sith","0000");

            try {
                authService.register("Sith", "1234");
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            try {
                User user2 = authService.login("Sith","0000");
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            FinanceService financeService =  new FinanceService();
            wallet1.setBudget("Еда", 1000);
            System.out.println(financeService.addExpense(user1, "Еда", 1000));
            System.out.println(financeService.addExpense(user1, "Еда", 1000));
            System.out.println(financeService.addExpense(user1, "Еда", 1000));
            System.out.println(financeService.addExpense(user1, "Еда", 1000));
            System.out.println(financeService.addExpense(user1, "Еда", 1000));
            System.out.println(financeService.addExpense(user1, "Еда", 1000));
            System.out.println(financeService.addExpense(user1, "Еда", 1000));
            System.out.println(financeService.addExpense(user1, "Еда", 1000));
            System.out.println(financeService.addExpense(user1, "Еда", 1000));

        }
}
