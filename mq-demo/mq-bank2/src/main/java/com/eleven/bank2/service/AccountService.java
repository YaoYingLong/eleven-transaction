package com.eleven.bank2.service;

import com.eleven.bank2.entity.AccountChangeEvent;

public interface AccountService {
    
    /**
     * 更新账户，增加金额
     */
    public void addAccountBalance(AccountChangeEvent accountChangeEvent);
    
}
