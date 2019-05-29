package com.so4it.dao;

import com.so4it.domain.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountDao {

    void create(Account account);

    Optional<Account> read(Long id);


    Collection<Account> readAccountsWithBalanceOver(Double limit);

}
