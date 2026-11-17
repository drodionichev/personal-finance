package ru.drodionichev.finance.service;

import org.junit.jupiter.api.Test;
import ru.drodionichev.finance.model.User;
import ru.drodionichev.finance.model.Wallet;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FinanceServiceTest {

    @Test
    void noAlertsWhenWithinBudget () {
        User user = new User("Sith", "1234");
        user.getWallet().addIncome("Зарплата", 1000);
        user.getWallet().setBudget("Еда", 1000);

        FinanceService financeService = new FinanceService();
        List<String> alerts = financeService.addExpense(user, "Еда", 500);

        assertTrue(alerts.isEmpty());
    }

    @Test
    void alertWhenBudgetExceeded(){
        User user = new User("Sith", "1234");
        user.getWallet().addIncome("Зарплата", 10000);
        user.getWallet().setBudget("Еда", 1000);

        FinanceService financeService = new FinanceService();
        List<String> alerts = financeService.addExpense(user, "Еда", 1500);

        assertEquals(1, alerts.size());
    }

    @Test
    void alertWhenBalanceNegative(){
        User user = new User("Sith", "1234");
        user.getWallet().addIncome("Зарплата", 100);

        FinanceService financeService = new FinanceService();
        List<String> alerts = financeService.addExpense(user, "Еда", 500);

        assertEquals(1, alerts.size());
    }

    @Test
    void twoAlertsWhenBudgetExceededAndBalanceNegative(){
        User user = new User("Sith", "1234");
        user.getWallet().addIncome("Зарплата", 100);
        user.getWallet().setBudget("Еда", 1000);

        FinanceService financeService = new FinanceService();
        List<String> alerts = financeService.addExpense(user, "Еда", 1500);

        assertEquals(2, alerts.size());
    }

    @Test
    void noAlertsWhenSpentExactlyLimitAndBalanceZero(){
        User user = new User("Sith", "1234");
        user.getWallet().addIncome("Зарплата", 1000);
        user.getWallet().setBudget("Еда", 1000);

        FinanceService financeService = new FinanceService();
        List<String> alerts = financeService.addExpense(user, "Еда", 1000);

        assertTrue(alerts.isEmpty());
    }
}