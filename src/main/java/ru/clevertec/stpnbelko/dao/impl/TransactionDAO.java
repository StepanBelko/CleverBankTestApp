package ru.clevertec.stpnbelko.dao.impl;

import ru.clevertec.stpnbelko.dao.AbstractDAO;
import ru.clevertec.stpnbelko.model.Transaction;
import ru.clevertec.stpnbelko.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class TransactionDAO extends AbstractDAO<Transaction> {
    @Override
    public boolean insert(Transaction transaction) {

        Connection connection = DBUtil.getConnection();
        String sql = "INSERT INTO transaction (operation_type, amount, from_account_number, to_account_number) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getTransactionType().getOperationType().descr);
            preparedStatement.setBigDecimal(2, transaction.getAmount());
            preparedStatement.setInt(3, transaction.getFromAccountNumber());
            preparedStatement.setInt(4, transaction.getToAccountNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.release(connection, null, preparedStatement, null);
        }

        return true;
    }

    @Override
    public boolean update(Transaction transaction) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Transaction transaction) {
        return false;
    }

    @Override
    public Transaction getById(int id) {
        return null;
    }

    @Override
    public Set<Transaction> getAll() {
        return null;
    }
}
