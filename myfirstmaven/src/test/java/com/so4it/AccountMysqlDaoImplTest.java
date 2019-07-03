package com.so4it;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountMysqlDaoImpl;
import com.so4it.domain.Account;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountMysqlDaoImplTest {

    @Test
    public void connectToDatabaseTest() {

        new AccountMysqlDaoImpl().connectToDatabase();

    }

    @Test
    public void createTest(){
        AccountDao dao = new AccountMysqlDaoImpl();
        Account account = Account.builder().withId(4L).withBalance(15000d).build();
        dao.create(account);
    }
}