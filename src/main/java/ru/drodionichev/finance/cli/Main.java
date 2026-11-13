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

            System.out.println(wallet.getBalance());
        }
}
