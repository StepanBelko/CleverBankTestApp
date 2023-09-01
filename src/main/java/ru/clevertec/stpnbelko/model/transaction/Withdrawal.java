package ru.clevertec.stpnbelko.model.transaction;

import lombok.Getter;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;
import ru.clevertec.stpnbelko.model.Transaction;

import java.math.BigDecimal;
import java.util.Scanner;

@Getter
public class Withdrawal extends TransactionType {

    public Withdrawal() {
        operationType = OperationType.WITHDRAWAL;
    }

    @Override
    public void fillTransaction(Account currentAccount, Transaction transaction) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Какую сумму вы хотите снять?");
        BigDecimal amount = BigDecimal.valueOf(-(scanner.nextDouble()));

        transaction.setAmount(amount);
        transaction.setFromAccountNumber(currentAccount.getId());
        transaction.setToAccountNumber(currentAccount.getId());

    }
}
