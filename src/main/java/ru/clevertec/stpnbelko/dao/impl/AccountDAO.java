package ru.clevertec.stpnbelko.dao.impl;

import ru.clevertec.stpnbelko.dao.AbstractDAO;
import ru.clevertec.stpnbelko.model.Account;
import ru.clevertec.stpnbelko.model.User;
import ru.clevertec.stpnbelko.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class AccountDAO extends AbstractDAO<Account> {

    @Override
    public boolean insert(Account account) {
        return false;
    }

    @Override
    public boolean update(Account account) {

        String sql = "UPDATE public.account SET\n" +
                "balance = ? ::numeric WHERE\n" +
                "id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setBigDecimal(1, account.getBalance());
            preparedStatement.setDouble(2, account.getId());

            preparedStatement.executeUpdate();
            System.out.println("Successfully updated account " + account.getId());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Account account) {
        return false;
    }

    @Override
    public Account getById(int id) {
        String sql = "SELECT * FROM public.account WHERE id = ?";
        Account account = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setOwnerId(resultSet.getInt("owner_id"));
                account.setBankId(resultSet.getInt("bank_id"));
                account.setBalance(resultSet.getBigDecimal("balance"));
                account.setCurrency(resultSet.getString("currency"));
                account.setOpeningDate(resultSet.getString("opening_date"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        return account;
    }

    @Override
    public Set<Account> getAll() {
        String sql = "SELECT * FROM public.account ORDER BY id ASC ";
        Set<Account> allAccounts = new LinkedHashSet<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setOwnerId(resultSet.getInt("owner_id"));
                account.setBankId(resultSet.getInt("bank_id"));
                account.setBalance(resultSet.getBigDecimal("balance"));
                account.setCurrency(resultSet.getString("currency"));
                account.setOpeningDate(resultSet.getString("opening_date"));
                allAccounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return allAccounts;
    }

    public Set<Account> getUserAccounts(User user) {

            String sql = "SELECT * FROM public.account WHERE owner_id = ?\n" +
                    "ORDER BY id ASC ";
            Set<Account> userAccounts = new LinkedHashSet<>();

            try (Connection connection = DBUtil.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, user.getId());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Account account = new Account();
                    account.setId(resultSet.getInt("id"));
                    account.setOwnerId(resultSet.getInt("owner_id"));
                    account.setBankId(resultSet.getInt("bank_id"));
                    account.setBalance(resultSet.getBigDecimal("balance"));
                    account.setCurrency(resultSet.getString("currency"));
                    account.setOpeningDate(resultSet.getString("opening_date"));
                    userAccounts.add(account);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

            return userAccounts;
        }

    }

