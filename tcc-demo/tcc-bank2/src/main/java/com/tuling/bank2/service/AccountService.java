package com.tuling.bank2.service;

public interface AccountService {
    
    /**
     * 增加账户余额
     * @param accountNo 账户
     * @param amount   增加的余额
     */
    public  void transferTo(String accountNo, Double amount);
}
