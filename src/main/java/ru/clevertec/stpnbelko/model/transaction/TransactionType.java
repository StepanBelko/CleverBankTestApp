package ru.clevertec.stpnbelko.model.transaction;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;
import ru.clevertec.stpnbelko.model.Transaction;

@Getter
@Setter
public abstract class TransactionType {

    OperationType operationType;

    public abstract void fillTransaction(Account currentAccount, Transaction transaction);
}
