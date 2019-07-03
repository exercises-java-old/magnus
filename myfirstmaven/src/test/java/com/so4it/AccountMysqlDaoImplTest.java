package com.so4it;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountMysqlDaoImpl;
import com.so4it.domain.Account;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class AccountMysqlDaoImplTest {

    @Test
    public void connectToDatabaseTest() {

        new AccountMysqlDaoImpl().connectToDatabase();
        //AccountDao dao = new AccountMysqlDaoImpl();
        //dao.connectToDatabase();
    }

    @Test
    public void createTest(){
        AccountDao dao = new AccountMysqlDaoImpl();
        Account account = Account.builder().withId(4L).withBalance(15000d).build();
        dao.create(account);
    }

    @Test
    public void readTest(){
        AccountDao dao = new AccountMysqlDaoImpl();
        dao.create(Account.builder().withId(6L).withBalance(9000d).build());
        Optional<Account> optional = dao.read(6L);
        Account account = optional.get();
        System.out.println(account.getBalance() );
    }

    @Test
    public void updateTest(){
        AccountDao dao = new AccountMysqlDaoImpl();
        dao.update(3L, 12000d);
        Assert.assertFalse(dao.update(10L, 2000d));
    }

}












