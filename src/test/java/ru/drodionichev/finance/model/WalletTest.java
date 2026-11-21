package ru.drodionichev.finance.model;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void balanceIsIncomeMinusExpenses() {
        // подготовка
        Wallet wallet = new Wallet();
        wallet.addIncome("Зарплата", 4000);

        // действие
        wallet.addExpense("Такси", 5000);

        // проверка
        assertEquals(-1000, wallet.getBalance());
    }

    @Test
    void totalIncomeSumsAllIncomes(){
        Wallet wallet = new Wallet();

        wallet.addIncome("Зарплата", 4000);
        wallet.addIncome("Бонусы", 5000);

        assertEquals(9000, wallet.getTotalIncome());
    }

    @Test
    void expensesByCategoryCountOnlyThisCategory(){
        Wallet wallet = new Wallet();
        wallet.addIncome("ЖКХ", 10000);
        wallet.addExpense("Такси", 5000);

        wallet.addExpense("ЖКХ", 10000);

        assertEquals(10000, wallet.getExpensesByCategory("ЖКХ"));
    }

    @Test
    void remainingBudgetIsLimitMinusExpenses(){
        Wallet wallet = new Wallet();
        wallet.setBudget("Такси",11000);

        wallet.addExpense("Такси", 5000);

        assertEquals(6000, wallet.getRemainingBudget("Такси"));
    }

    @Test
    void remainingBudgetIsZeroWhenSpentExactlyLimit(){
        Wallet wallet = new Wallet();
        wallet.setBudget("Такси",5000);

        wallet.addExpense("Такси", 5000);

        assertEquals(0, wallet.getRemainingBudget("Такси"));
    }

    @Test
    void zeroAmountIsRejected() {
        Wallet wallet = new Wallet();

        assertThrows(IllegalArgumentException.class,
                () -> wallet.addIncome("Зарплата", 0));
    }

    @Test
    void emptyCategoryIsRejected() {
        Wallet wallet = new Wallet();

        assertThrows(IllegalArgumentException.class,
                () -> wallet.setBudget("", 100));
    }

    @Test
    void zeroLimitIsRejected() {
        Wallet wallet = new Wallet();

        assertThrows(IllegalArgumentException.class,
                () -> wallet.setBudget("Такси", 0));
    }

    @Test
    void negativeLimitIsRejected() {
        Wallet wallet = new Wallet();

        assertThrows(IllegalArgumentException.class,
                () -> wallet.setBudget("Такси", -1000));
    }

    @Test
    void getBudgetWithoutLimitThrows() {
        Wallet wallet = new Wallet();

        assertThrows(IllegalArgumentException.class,
                () -> wallet.getBudget("Такси"));
    }

    @Test
    void incomeTotalsGroupedByCategory(){
        Wallet wallet = new Wallet();

        // Доходы = 15000, 3000
        wallet.addIncome("Зарплата", 10000);
        wallet.addIncome("Зарплата", 5000);
        wallet.addIncome("Бонус", 3000);

        // Расходы = 6000, 7000
        wallet.addExpense("Игры", 1000);
        wallet.addExpense("Игры", 5000);
        wallet.addExpense("ЖКХ", 7000);

        assertEquals(Map.of("Зарплата", 15000.0, "Бонус", 3000.0), wallet.getIncomeTotalsByCategory());
    }

    @Test
    void expenseTotalsGroupedByCategory(){
        Wallet wallet = new Wallet();

        // Доходы = 15000, 3000
        wallet.addIncome("Зарплата", 10000);
        wallet.addIncome("Зарплата", 5000);
        wallet.addIncome("Бонус", 3000);

        // Расходы = 6000, 7000
        wallet.addExpense("Игры", 1000);
        wallet.addExpense("Игры", 5000);
        wallet.addExpense("ЖКХ", 7000);


        assertEquals(Map.of("Игры", 6000.0, "ЖКХ", 7000.0), wallet.getExpenseTotalsByCategory());
    }

}