package com.so4it.service;

import com.so4it.domain.Account;

public interface AccountService {
    Double getBalance(Long id);

    void create(Account account);
}
