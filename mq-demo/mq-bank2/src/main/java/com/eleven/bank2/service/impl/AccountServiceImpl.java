package com.eleven.bank2.service.impl;

import com.eleven.bank2.service.AccountService;
import com.eleven.bank2.entity.AccountChangeEvent;
import com.eleven.bank2.mapper.AccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    
    @Autowired
    AccountMapper accountMapper;
    
    /**
     * 更新账户，增加金额
     * @param accountChangeEvent
     */
    @Override
    @Transactional
    public void addAccountBalance(AccountChangeEvent accountChangeEvent) {
        log.info("bank2更新本地账号，账号：{},金额：{}",accountChangeEvent.getToAccountNo(),accountChangeEvent.getAmount());
        //幂等性校验
        if(accountMapper.isExistTx(accountChangeEvent.getTxNo())>0){
            return ;
        }
        //增加金额
        accountMapper.addAccountBalance(accountChangeEvent.getToAccountNo(),accountChangeEvent.getAmount());
        //添加事务记录，用于幂等
        accountMapper.addTx(accountChangeEvent.getTxNo());

        if(accountChangeEvent.getAmount() == 40){
            throw new RuntimeException("模拟异常");
        }

    }
}
