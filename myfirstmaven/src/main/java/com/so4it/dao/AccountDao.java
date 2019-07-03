package com.so4it.dao;

import com.so4it.domain.Account;

import java.sql.Connection;
import java.util.Collection;
import java.util.Optional;

public interface AccountDao {

    void create(Account account);

    Optional<Account> read(Long id);

    boolean update(Account account);

    boolean update(Long id, Double newBalance);

    boolean delete(Long id);

    Connection connectToDatabase();

    Collection<Account> readAccountsWithBalanceOver(Double limit);

}
