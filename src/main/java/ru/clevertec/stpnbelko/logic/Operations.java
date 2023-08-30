package ru.clevertec.stpnbelko.logic;

import ru.clevertec.stpnbelko.dao.impl.AccountDAO;
import ru.clevertec.stpnbelko.dao.impl.TransactionDAO;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.Transaction;

public class Operations {

    public static boolean doOperation(Account currentAccount, Transaction transaction) {

        AccountDAO accDao = new AccountDAO();

        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.insert(transaction);

        if (currentAccount.getId() == transaction.getToAccountNumber()) {
            currentAccount.setBalance(currentAccount.getBalance().add(transaction.getAmount()));
            accDao.update(currentAccount);
        } else {
            currentAccount.setBalance(currentAccount.getBalance().subtract(transaction.getAmount()));
            accDao.update(currentAccount);

            Account recipientAccount = accDao.getById(transaction.getToAccountNumber());
            recipientAccount.setBalance(recipientAccount.getBalance().add(transaction.getAmount()));
            accDao.update(recipientAccount);
        }

        System.out.println("Текущий баланс: " + currentAccount.getBalance() + " "
                + currentAccount.getCurrency());

        return true;
    }
}