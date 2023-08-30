package ru.clevertec.stpnbelko.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Account {

    private int id;
    private int ownerId;
    private int bankId;
    private BigDecimal balance;
    private String currency;
    private String openingDate;

}
