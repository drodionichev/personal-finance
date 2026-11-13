package ru.drodionichev.finance.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wallet {
    final private List<Operation> operations = new ArrayList<>();

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
}

