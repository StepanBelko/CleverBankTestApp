package ru.clevertec.stpnbelko.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Receipt {
    private int id;
    private Timestamp date;
    private String operationType;
    private String fromBankName;
    private String toBankName;
    private int fromAccountNumber;
    private int toAccountNumber;
    private BigDecimal amount;


    @Override
    public String toString() {
        return "ЧЕК!!!!!!{" +
                "id=" + id +
                ", date=" + date +
                ", operationType='" + operationType + '\'' +
                ", fromBankName='" + fromBankName + '\'' +
                ", toBankName='" + toBankName + '\'' +
                ", fromAccountNumber=" + fromAccountNumber +
                ", toAccountNumber=" + toAccountNumber +
                ", amount=" + amount +
                '}';
    }
}
