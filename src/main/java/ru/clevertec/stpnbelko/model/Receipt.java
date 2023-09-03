package ru.clevertec.stpnbelko.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Receipt {
    private int id;
    private Timestamp currentTime;
    private OperationType operationType;
    private String fromBankName;
    private String toBankName;
    private int fromAccountNumber;
    private int toAccountNumber;
    private BigDecimal amount;
    private String currency;

    @Override
    public String toString() {
        return "Банковский чек {" +
                "id=" + id +
                ", date=" + currentTime +
                ", operationType='" + operationType + '\'' +
                ", fromBankName='" + fromBankName + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", fromAccountNumber=" + fromAccountNumber +
                ", toAccountNumber=" + toAccountNumber +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
