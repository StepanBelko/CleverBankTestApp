package ru.clevertec.stpnbelko.dao.impl;

import ru.clevertec.stpnbelko.dao.AbstractDAO;
import ru.clevertec.stpnbelko.model.transaction.Transaction;
import ru.clevertec.stpnbelko.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class TransactionDAO extends AbstractDAO<Transaction> {

    @Override
    public int insert(Transaction transaction) {
        int currentTransId = 0;

        Connection connection = DBUtil.getConnection();
        String sql = "INSERT INTO transaction (operation_type, amount, from_account_number, to_account_number) VALUES (?, ?, ?, ?)";
        String sql2 = "SELECT id FROM public.transaction\n" +
                "ORDER BY id DESC  LIMIT 1";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getTransactionType().getOperationType().descr);
            preparedStatement.setBigDecimal(2, transaction.getAmount());
            preparedStatement.setInt(3, transaction.getFromAccountNumber());
            preparedStatement.setInt(4, transaction.getToAccountNumber());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(sql2);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                currentTransId = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.release(connection, null, preparedStatement, null);
        }

        return currentTransId;
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
        String sql = "SELECT * FROM transaction WHERE id = ?";

        Transaction transaction = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                transaction = new Transaction();
                transaction.setId(resultSet.getInt("id"));
                transaction.setDate(resultSet.getTimestamp("date"));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setFromAccountNumber(resultSet.getInt("from_account_number"));
                transaction.setToAccountNumber(resultSet.getInt("to_account_number"));

            } else {
                System.out.println("Transaction does not exist. id =  " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return transaction;
    }

    @Override
    public Set<Transaction> getAll() {
        return null;
    }

}
