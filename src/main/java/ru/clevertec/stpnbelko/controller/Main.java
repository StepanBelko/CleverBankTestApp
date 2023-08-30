package ru.clevertec.stpnbelko.controller;

import ru.clevertec.stpnbelko.dao.impl.AccountDAO;
import ru.clevertec.stpnbelko.dao.impl.UserDAO;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;
import ru.clevertec.stpnbelko.model.Transaction;
import ru.clevertec.stpnbelko.model.User;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;

import static ru.clevertec.stpnbelko.logic.Operations.doOperation;

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

        System.out.println("Выберите счёт (введите номер)");
        for (Account account : accDao.getUserAccounts(user)) {
            System.out.println(account.getId() + " "
                    + account.getBalance() + " "
                    + account.getCurrency() + " "
                    + account.getBankId());
        }

        int accountId = scanner.nextInt();
        Account currentAccount = accDao.getById(accountId);


        System.out.println("Выберите операцию:");

        for (OperationType type : OperationType.values()) {
            System.out.println(type.id + " " + type.descr);
        }
        int choice = scanner.nextInt();

        Transaction transaction = new Transaction();

        for (OperationType type : OperationType.values()) {
            if (type.id == choice) {
                transaction.setOperationType(type);
            }
        }
        System.out.println("Текущий баланс: " + currentAccount.getBalance() + " " + currentAccount.getCurrency());

        switch (transaction.getOperationType()) {
            case TRANSFER -> {
                System.out.println("Выберите получателя (введите 10-значный номер счёта)");
                transaction.setToAccountNumber(scanner.nextInt());
                transaction.setFromAccountNumber(currentAccount.getId());

                System.out.println("Какую сумму вы хотите отправить?");

                BigDecimal amount = null;
                do {
                    System.out.println("Не больше " + currentAccount.getBalance() + " " + currentAccount.getCurrency());
                    amount = BigDecimal.valueOf(scanner.nextDouble());
                } while (currentAccount.getBalance().compareTo(amount) == -1 );
                transaction.setAmount(amount);

                doOperation(currentAccount, transaction);
            }


            case WITHDRAWAL -> {
                System.out.println("Какую сумму вы хотите снять?");
                BigDecimal amount = BigDecimal.valueOf(-(scanner.nextDouble()));
                transaction.setAmount(amount);
                transaction.setFromAccountNumber(currentAccount.getId());
                transaction.setToAccountNumber(currentAccount.getId());
                doOperation(currentAccount, transaction);
            }
            case REFIL -> {
                System.out.println("Какую сумму вы хотите внести?");
                BigDecimal amount = BigDecimal.valueOf(scanner.nextDouble());
                transaction.setAmount(amount);
                transaction.setFromAccountNumber(currentAccount.getId());
                transaction.setToAccountNumber(currentAccount.getId());
                doOperation(currentAccount, transaction);
            }
            case STATEMENT -> {
                System.out.println(4);
            }
        }


    }
}

    
