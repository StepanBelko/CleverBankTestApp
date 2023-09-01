package ru.clevertec.stpnbelko.model;


import lombok.Getter;
import lombok.Setter;
import ru.clevertec.stpnbelko.model.transaction.TransactionType;

import java.math.BigDecimal;

@Getter
@Setter

public class Transaction {
    private int id;
    private String date;
    private BigDecimal amount;
    private int fromAccountNumber;
    private int toAccountNumber;
    private TransactionType transactionType;

    public Transaction(TransactionType transactionType, Account currentAccount) {
        this.transactionType = transactionType;
        transactionType.fillTransaction(currentAccount, this);
    }
}

