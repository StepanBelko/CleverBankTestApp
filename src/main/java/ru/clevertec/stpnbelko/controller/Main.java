package ru.clevertec.stpnbelko.controller;

import ru.clevertec.stpnbelko.dao.impl.AccountDAO;
import ru.clevertec.stpnbelko.dao.impl.UserDAO;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;
import ru.clevertec.stpnbelko.model.Transaction;
import ru.clevertec.stpnbelko.model.User;
import ru.clevertec.stpnbelko.model.transaction.Refil;
import ru.clevertec.stpnbelko.model.transaction.TransactionType;
import ru.clevertec.stpnbelko.model.transaction.Transfer;
import ru.clevertec.stpnbelko.model.transaction.Withdrawal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import static ru.clevertec.stpnbelko.logic.Operations.doOperation;
import static ru.clevertec.stpnbelko.model.OperationType.*;

public class Main {

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите id пользователя");
        UserDAO userDAO = new UserDAO();
        Set<User> userSet = userDAO.getAll();
        AccountDAO accDao = new AccountDAO();

        for (User user : userSet) {
            System.out.println(user.getId() + " - " + user.getFirstName() + " " + user.getLastName());
        }
        int userId = scanner.nextInt();
        User user = userDAO.getById(userId);
        System.out.println(user.toString());


        HashMap<Integer, Object> accountMap = new HashMap<>();
        int i = 1;
        System.out.println("Выберите счёт (введите номер)");
        for (Account account : accDao.getUserAccounts(user)) {
            System.out.println("№" + i + " - " + account.getId() + " "
                    + account.getBalance() + " "
                    + account.getCurrency() + " "
                    + account.getBankId());
            accountMap.put(i++, account);
        }
        int accountId = scanner.nextInt();
        Account currentAccount = (Account) accountMap.get(accountId);

        
        System.out.println("\nВыберите операцию: \n" + "1 .. " + values().length);
        for (OperationType type : OperationType.values()) {
            System.out.println(type.id + " " + type.descr);
        }
        int choice = scanner.nextInt();

        System.out.println("Текущий баланс: " + currentAccount.getBalance()
                + " " + currentAccount.getCurrency());

        Transaction transaction = null;

        if (choice == TRANSFER.id) {
            transaction = new Transaction(new Transfer(), currentAccount);
        } else if (choice == WITHDRAWAL.id) {
            transaction = new Transaction(new Withdrawal(), currentAccount);
        } else if (choice == REFIL.id) {
            transaction = new Transaction(new Refil(), currentAccount);
        } else if (choice == STATEMENT.id) {
            System.out.println(4);
        }


        doOperation(currentAccount, transaction);


    }
}

    
