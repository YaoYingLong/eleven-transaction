package com.tuling.bank1.controller;

import com.tuling.bank1.entity.AccountChangeEvent;
import com.tuling.bank1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/bank1")
public class Bank1Controller {
    @Autowired
    AccountService accountService;
    
    /**
     * 转账接口
     * @param from_accountNo  转出账户
     * @param to_accountNo   转入账户
     * @param amount        转账的金额
     * @return
     */
    @RequestMapping("/transfer")
    public String transfer(@RequestParam("from_accountNo") String from_accountNo,
            @RequestParam("to_accountNo") String to_accountNo,
            @RequestParam("amount") Double amount) {
    
        //创建一个事务id，作为消息内容发到mq  全局唯一id
        String tx_no = UUID.randomUUID().toString();
        AccountChangeEvent accountChangeEvent = new AccountChangeEvent(from_accountNo,to_accountNo,amount,tx_no);
        //发送消息
        accountService.sendTansaferAccount(accountChangeEvent);
        return "ok";
    }

}