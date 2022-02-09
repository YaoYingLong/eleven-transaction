package com.eleven.bank1.service;

public interface AccountService {
    
    /**
     * 转账
     * @param fromAccountNo  转出账户
     * @param toAccountNo   转入账户
     * @param amount   转账的金额
     */
    void transfer(String fromAccountNo,String toAccountNo, Double amount);
}
