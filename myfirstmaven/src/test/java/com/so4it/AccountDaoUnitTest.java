package com.so4it;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import org.junit.Assert;
import org.junit.Test;

public class AccountDaoUnitTest {

    @Test
    public void testCreateAndRead() {
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.create(Account.builder().withId(1L).build());
        Assert.assertNotNull(accountDao.read(1L));
    }
    @Test
    public void testReadAccountsWithLimitAbove() {
        AccountDao accountDao = new AccountDaoImpl();
        accountDao.create(Account.builder().withId(1L).withBalance(101d).build());
        accountDao.create(Account.builder().withId(2L).withBalance(99d).build());
        accountDao.create(Account.builder().withId(3L).withBalance(100d).build());
        Assert.assertEquals(2,accountDao.readAccountsWithBalanceOver(99d).size());
    }

}
