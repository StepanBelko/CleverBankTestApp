package ru.clevertec.stpnbelko.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor

public class Transaction {
    private int id;
    private OperationType operationType;
    private String date;
    private BigDecimal amount;
    private int fromAccountNumber;
    private int toAccountNumber;

    public Transaction(OperationType operationType, BigDecimal amount, int fromAccountNumber, int toAccountNumber) {
        this.operationType = operationType;
        this.amount = amount;
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
    }
}

