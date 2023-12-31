package ru.clevertec.stpnbelko.logic;

import ru.clevertec.stpnbelko.dao.impl.AccountDAO;
import ru.clevertec.stpnbelko.dao.impl.TransactionDAO;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;
import ru.clevertec.stpnbelko.model.Receipt;
import ru.clevertec.stpnbelko.model.Statement;
import ru.clevertec.stpnbelko.model.Transaction;

import static ru.clevertec.stpnbelko.output.Writer.*;

public class Operations {

    public static boolean doOperation(Account currentAccount, Transaction transaction) {

        AccountDAO accDao = new AccountDAO();

        TransactionDAO transactionDAO = new TransactionDAO();
        int currentTransId = transactionDAO.insert(transaction);

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


        if (transaction.getOperationType() != OperationType.STATEMENT) {
            Receipt receipt = transaction.makeReceipt(currentTransId);
            receipt.setAmount(transaction.getAmount());
            writeToConsole(createCheckFromReceipt(receipt));
            writeToFile(receipt);
        } else {
            Statement statement = new Statement(currentAccount, transactionDAO.getById(currentTransId));
            writeToConsole(createCheckFromStatement(statement));
            writeToFile(statement);
        }


        return true;
    }
}
