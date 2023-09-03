package ru.clevertec.stpnbelko.model.transaction;

import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;

import java.math.BigDecimal;
import java.util.Scanner;

public class Transfer extends TransactionType {
    public Transfer() {
        operationType = OperationType.TRANSFER;
    }

    @Override
    public void fillTransaction(Account currentAccount, Transaction transaction) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите получателя (введите 10-значный номер счёта)");
        transaction.setFromAccountNumber(currentAccount.getId());
        transaction.setToAccountNumber(scanner.nextInt());

        System.out.println("Какую сумму вы хотите отправить?");

        BigDecimal amount = null;
        do {
            System.out.println("Не больше " + currentAccount.getBalance() + " " + currentAccount.getCurrency());
            amount = BigDecimal.valueOf(scanner.nextDouble());
        } while (currentAccount.getBalance().compareTo(amount) == -1);
        transaction.setAmount(amount);
    }

}
