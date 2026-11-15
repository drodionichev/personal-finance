package ru.drodionichev.finance.cli;

import ru.drodionichev.finance.model.User;
import ru.drodionichev.finance.model.Wallet;
import ru.drodionichev.finance.service.AuthService;
import ru.drodionichev.finance.service.FinanceService;

public class Main {
        public static void main(String[] args) {

            ConsoleApp consoleApp = new ConsoleApp();
            consoleApp.run();
        }
}
