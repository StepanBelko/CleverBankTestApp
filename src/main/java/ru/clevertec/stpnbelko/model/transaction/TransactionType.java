package ru.clevertec.stpnbelko.model.transaction;


import lombok.Getter;
import lombok.Setter;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;
import ru.clevertec.stpnbelko.model.Receipt;
import ru.clevertec.stpnbelko.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public abstract class TransactionType {

    OperationType operationType;

    public abstract void fillTransaction(Account currentAccount, Transaction transaction);

}
