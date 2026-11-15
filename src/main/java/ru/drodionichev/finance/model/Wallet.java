package ru.drodionichev.finance.model;

import java.io.Serializable;
import java.util.*;

public class Wallet{
    private final  List<Operation> operations = new ArrayList<>();
    private final  Map<String, Double> budgets = new LinkedHashMap<>();

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

    // сумма всех доходов
    public double getTotalIncome(){
        double income = 0;
        for(Operation operation : operations){
            if(operation.isIncome()){
                income += operation.getAmount();
            }
        }
        return income;
    }

    // сумма всех расходов
    public double getTotalExpenses(){
        double expenses = 0;
        for(Operation operation : operations){
            if(!operation.isIncome()){
                expenses += operation.getAmount();
            }
        }
        return expenses;
    }

    // сумма расходов одной категории
    public double getExpensesByCategory(String category){
        double expenses = 0;
        for (Operation operation : operations){
            if(!operation.isIncome() && operation.getCategory().equals(category)){
                expenses += operation.getAmount();
            }
        }
        return expenses;
    }

    public double getRemainingBudget(String category){
        return getBudget(category) - getExpensesByCategory(category);
    }

    public Map<String, Double> getBudgets(){
        return Collections.unmodifiableMap(budgets);
    }
}

