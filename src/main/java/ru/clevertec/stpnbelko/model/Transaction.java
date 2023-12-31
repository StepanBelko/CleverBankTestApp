package ru.clevertec.stpnbelko.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.OperationType;
import ru.clevertec.stpnbelko.model.Receipt;
import ru.clevertec.stpnbelko.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    private int id;
    private Timestamp date;
    private BigDecimal amount;
    private int fromAccountNumber;
    private int toAccountNumber;
    private OperationType operationType;


    public Transaction(OperationType operationType, Account currentAccount) {
        this.operationType = operationType;
        this.operationType.fillTransaction(currentAccount, this);
    }

    public Receipt makeReceipt(int currentTransId) {
        String sql = "SELECT t.id,\n" +
                "t.date,\n" +
                "t.operation_type,\n" +
                "b.name as from_bank_name,\n" +
                "b2.name as to_bank_name,\n" +
                "t.from_account_number,\n" +
                "t.to_account_number,\n" +
                "t.amount\n," +
                "a1.currency\n" +
                "FROM public.transaction as t\n" +
                "LEFT JOIN public.account as a1\n" +
                "ON a1.id = t.from_account_number\n" +
                "JOIN public.bank as b\n" +
                "on b.id = a1.bank_id\n" +
                "LEFT JOIN public.account as a2\n" +
                "ON a2.id = t.to_account_number\n" +
                "JOIN public.bank as b2\n" +
                "on b2.id = a2.bank_id\n" +
                "WHERE t.id = ?";
        Receipt reciept = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, currentTransId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                reciept = new Receipt();
                reciept.setId(resultSet.getInt("id"));
                reciept.setCurrentTime(resultSet.getTimestamp("date"));

                String operation_type = resultSet.getString("operation_type");

                if (Objects.equals(operation_type, OperationType.TRANSFER.descr)) {
                    reciept.setOperationType(OperationType.TRANSFER);
                } else if (Objects.equals(operation_type, OperationType.WITHDRAWAL.descr)) {
                    reciept.setOperationType(OperationType.WITHDRAWAL);
                } else if (Objects.equals(operation_type, OperationType.REFIL.descr)) {
                    reciept.setOperationType(OperationType.REFIL);
                } else if (Objects.equals(operation_type, OperationType.STATEMENT.descr)) {
                    reciept.setOperationType(OperationType.STATEMENT);
                }

                reciept.setFromBankName(resultSet.getString("from_bank_name"));
                reciept.setToBankName(resultSet.getString("to_bank_name"));
                reciept.setFromAccountNumber(resultSet.getInt("from_account_number"));
                reciept.setToAccountNumber(resultSet.getInt("to_account_number"));
                reciept.setAmount(resultSet.getBigDecimal("amount"));
                reciept.setCurrency(resultSet.getString("currency"));

            } else {
                System.out.println("Something wrong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reciept;
    }
}

