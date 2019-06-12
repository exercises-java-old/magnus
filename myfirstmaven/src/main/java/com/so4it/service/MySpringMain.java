package com.so4it.service;

import com.so4it.dao.AccountDao;
import com.so4it.domain.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import java.util.Optional;

public class MySpringMain {


    public static void main(String[] args){

        ApplicationContext applicationContext = new GenericXmlApplicationContext("MySpringMain.xml");
        AccountDao dao = applicationContext.getBean(AccountDao.class);
        Optional<Account> optional = dao.read(1L);
        System.out.println("isPresent:" + optional.isPresent());

        dao.create(Account.builder().withId(1L).withBalance(2.0d).build());
        optional = dao.read(1L);
        System.out.println("isPresent:" + optional.isPresent());

        AccountService accountService = applicationContext.getBean(AccountService.class);
        System.out.println(accountService.getBalance(1L));
    }
}
