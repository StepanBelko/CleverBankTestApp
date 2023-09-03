package ru.clevertec.stpnbelko.model.transaction;


import lombok.Getter;
import lombok.Setter;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;

@Getter
@Setter
public abstract class TransactionType {

    OperationType operationType;

    public abstract void fillTransaction(Account currentAccount, Transaction transaction);

}
