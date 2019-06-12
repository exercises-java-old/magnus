package com.so4it;

import com.so4it.dao.AccountDao;
import com.so4it.dao.AccountDaoImpl;
import com.so4it.domain.Account;
import com.so4it.service.*;
import org.junit.Assert;
import org.junit.Test;

public class AccountServiceFactoryUnitTest {

    @Test
    public void testGetBalanceHardcoded() throws Exception{
        getBalanceDynamically("com.so4it.service.ServiceFactoryImplOne").getBalance(1L);
        getBalanceDynamically("com.so4it.service.ServiceFactoryImplTwo").getBalance(1L);
    }

    public AccountService getBalanceDynamically(String clazz) throws Exception{
        ServiceFactory serviceFactory = (ServiceFactory)Class.forName(clazz).getDeclaredConstructor().newInstance();
        return serviceFactory.createAccountService();
    }

}
