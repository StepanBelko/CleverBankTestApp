package ru.clevertec.stpnbelko.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.clevertec.stpnbelko.util.DBUtil;

import java.math.BigDecimal;
import java.sql.*;

@Getter
@AllArgsConstructor


public class Statement {
    private String ownerFullName;
    private int id;
    private String bankName;
    private String currency;
    private Timestamp openingDate;
    private Timestamp checkDate;
    private Timestamp currentTime;
    private BigDecimal balance;
    private BigDecimal totalSpending;
    private BigDecimal totalIncome;

    @Override
    public String toString() {
        return "Statement{" +
                "ownerFullName='" + ownerFullName + '\'' +
                ", id=" + id +
                ", bankName='" + bankName + '\'' +
                ", currency='" + currency + '\'' +
                ", openingDate=" + openingDate +
                ", checkDate=" + checkDate +
                ", currentTime=" + currentTime +
                ", balance=" + balance +
                ", totalSpending=" + totalSpending +
                ", totalIncome=" + totalIncome +
                '}';
    }

    public Statement(Account currentAccount, Transaction transaction) {
        this.id = currentAccount.getId();
        this.balance = currentAccount.getBalance();
        this.currency = currentAccount.getCurrency();
        this.openingDate = currentAccount.getOpeningDate();
        this.checkDate = transaction.getDate();  // добавить System.in;
        this.currentTime = transaction.getDate();
        this.totalIncome = getTotalIncome(currentAccount);
        this.totalSpending = getTotalSpending(currentAccount);

        fillInTheMissingFields(currentAccount);

    }

    public void fillInTheMissingFields(Account currentAccount) {

        String sql = "SELECT \n" +
                "a.id as account_number, \n" +
                "b.name,\n" +
                "u.first_name,\n" +
                "u.last_name,\n" +
                "a.balance, \n" +
                "a.currency,\n" +
                "a.opening_date\n" +
                "FROM account as a\n" +
                "JOIN bank as b \n" +
                "ON b.id = a.bank_id\n" +
                "JOIN users as u\n" +
                "ON a.owner_id = u.id\n" +
                "\n" +
                "WHERE a.id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, currentAccount.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.ownerFullName = resultSet.getString("first_name") + " "
                        + resultSet.getString("last_name");
                this.bankName = resultSet.getString("name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getTotalSpending(Account currentAccount) {
        String sql = "SELECT * FROM transaction \n" +
                "WHERE (amount < 0 AND from_account_number = ?)\n" +
                "OR (amount > 0 AND from_account_number = ? \n" +
                "\tAND (operation_type = 'Перевод' OR operation_type = 'TRANSFER'))\n";

        BigDecimal total;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, currentAccount.getId());
            preparedStatement.setInt(2, currentAccount.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            total = new BigDecimal(0);

            while (resultSet.next()) {
                total = total.add(resultSet.getBigDecimal("amount").abs());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return total;
    }


    private BigDecimal getTotalIncome(Account currentAccount) {

        String sql = "SELECT * FROM transaction \n" +
                "WHERE amount > 0 and to_account_number = ?";

        BigDecimal total;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, currentAccount.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            total = new BigDecimal(0);

            while (resultSet.next()) {
                total = total.add(resultSet.getBigDecimal("amount"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return total;
    }

}
