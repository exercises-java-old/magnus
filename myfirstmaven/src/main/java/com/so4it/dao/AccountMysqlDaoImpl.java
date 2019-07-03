package com.so4it.dao;

import com.so4it.domain.Account;

import java.sql.*;
import java.util.Collection;
import java.util.Optional;

public class AccountMysqlDaoImpl implements AccountDao {

    private Connection connection = null;

    public Connection connectToDatabase() {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lexicon" +
                            "?useUnicode=true" +
                            "&useJDBCCompliantTimezoneShift=true" +
                            "&useLegacyDatetimeCode=false" +
                            "&serverTimezone=UTC" +
                            "&autoReconnect=true" +
                            "&useSSL=false",
                    "root", "lexicon");
            System.out.println("Connection to database established");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to database");
        }

        return null;
    }

    @Override
    public void create(Account account) {
        Connection connection = connectToDatabase();

        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO accounts VALUES(?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, account.getId());
            preparedStatement.setDouble(2, account.getBalance());

            preparedStatement.executeUpdate();
            System.out.println("Great Success!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally { try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }
    
    @Override
    public Optional<Account> read(Long id) {
        Connection connection = connectToDatabase();
        PreparedStatement preparedStatement = null;

        Account account;
        Long accountId = null;
        Double accountBalance = null;

        String sql = "SELECT id, balance FROM accounts WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                accountId = resultSet.getLong(1); //resultSet.getLong("id");
                accountBalance = resultSet.getDouble(2); //resultSet.getDouble("balance");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            account = Account.builder().withId(accountId).withBalance(accountBalance).build();
        }
        catch (NullPointerException e){
            System.out.println("Account with id "+id+" could not be found.");
            return Optional.empty();
        }finally { try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

        return Optional.ofNullable(account);
    }

    @Override
    public boolean update(Account account) {
        return false;
    }

    @Override
    public boolean update(Long id, Double newBalance) {
        Connection connection = connectToDatabase();
        PreparedStatement preparedStatement = null;
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
        int rowsAffected = 0;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setLong(2, id);
            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsAffected == 1;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }


    @Override
    public Collection<Account> readAccountsWithBalanceOver(Double limit) {
        return null;
    }
}








