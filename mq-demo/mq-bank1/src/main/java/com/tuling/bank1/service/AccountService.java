package com.tuling.bank1.service;

import com.tuling.bank1.entity.AccountChangeEvent;

public interface AccountService {
    
    /**
     * 向mq发送转账消息
     * @param accountChangeEvent
     */
    public void sendTansaferAccount(AccountChangeEvent accountChangeEvent);
    
    /**
     * 更新账户，扣减金额
     * @param accountChangeEvent
     */
    public void reduceAccountBalance(AccountChangeEvent accountChangeEvent);
}
