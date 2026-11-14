package ru.drodionichev.finance.service;

import ru.drodionichev.finance.model.User;
import ru.drodionichev.finance.model.Wallet;

import java.util.ArrayList;
import java.util.List;

public class FinanceService {

    public List<String> addExpense(User user, String category, double amount){
        List<String> alerts = new ArrayList<>();
        Wallet wallet = user.getWallet();
        wallet.addExpense(category, amount);

        if (wallet.hasBudget(category) && wallet.getRemainingBudget(category) < 0){
            alerts.add("Бро, в категории: " + category + " жестко много тратишь, давай поменьше завозиков");
        }

        if (wallet.getBalance() < 0){
            alerts.add("Бро, аларм, баланс меньше нуля(");
        }
        return alerts;
    }
}
