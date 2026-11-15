package ru.drodionichev.finance.model;

public class Operation {
    private String category;
    private double amount;
    private boolean income;

    public Operation(String category, double amount, boolean income) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше нулю");
        }

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Категория должна быть не пустой");
        }

        this.category = category;
        this.amount = amount;
        this.income = income;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIncome() {
        return income;
    }
}
