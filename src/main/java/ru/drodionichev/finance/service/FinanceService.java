package ru.drodionichev.finance.service;

import ru.drodionichev.finance.model.User;
import ru.drodionichev.finance.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class FinanceService {

    public List<String> addExpense(User user, String category, double amount) {
        List<String> alerts = new ArrayList<>();
        Wallet wallet = user.getWallet();
        wallet.addExpense(category, amount);

        if (wallet.hasBudget(category)) {
            double expense = wallet.getExpensesByCategory(category);
            double limit = wallet.getBudget(category);

            if (expense > limit) {
                alerts.add("В категории: " + category + " затраты больше установленного лимита в: " + limit);
            } else if ((expense / limit) * 100 >= 80) {
                alerts.add("В категории: " + category + " осталось меньше 20% бюджета: " + (limit - expense));
            }
        }

        if (wallet.getBalance() == 0){
            alerts.add("Баланс равен нулю ");
        } else if (wallet.getBalance() < 0){
            alerts.add("Баланс меньше нуля(");
        }

        return alerts;
    }
}
