package ru.drodionichev.finance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    private final  List<Operation> operations = new ArrayList<>();
    private final  Map<String, Double> budgets = new HashMap<>();

    public Wallet() {
    }

    public void addIncome(String category, double amount) {
        operations.add(new Operation(category, amount,true));
    }

    public void addExpense(String category, double amount) {
        operations.add(new Operation(category, amount,false));
    }

    public double getBalance() {
        double balance = 0;
        for (Operation operation : operations) {
            if (operation.isIncome()){
                balance += operation.getAmount();
            } else{
                balance -= operation.getAmount();
            }
        }
        return balance;
    }

    public List<Operation> getOperation(){
        return Collections.unmodifiableList(operations);
    }

    public void setBudget(String category, double limit){
        if(category == null || category.isBlank()){
            throw new IllegalArgumentException("Категория не может быть пустой");
        }

        if(limit <= 0) {
            throw new IllegalArgumentException("Лимит должен быть больше нуля");
        }

        budgets.put(category, limit);
    }

    public boolean hasBudget(String category) {
        return budgets.containsKey(category);
    }

    public double getBudget(String category){
        if (!hasBudget(category)){
            throw new IllegalArgumentException("Бюджет для категории " + category + " не задан");
        }

        return budgets.get(category);
    }
}

