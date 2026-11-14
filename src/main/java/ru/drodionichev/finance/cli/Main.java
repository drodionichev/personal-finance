package ru.drodionichev.finance.cli;

import ru.drodionichev.finance.model.Wallet;

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
        }
}
