package ru.clevertec.stpnbelko.model.transaction;

import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Statement {
    private String owner;
    private int id;
    private String currency;
    private Timestamp openingDate;
    private Timestamp chackDate;
    private Timestamp currentTime;
    private BigDecimal balance;
    private BigDecimal totalSpending;
    private BigDecimal totalIncome;

}
