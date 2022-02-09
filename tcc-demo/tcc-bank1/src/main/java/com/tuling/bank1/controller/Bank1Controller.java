package com.tuling.bank1.controller;

import com.tuling.bank1.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank1")
public class Bank1Controller {
    @Autowired
    AccountService accountService;

    /**
     * 转账接口
     *
     * @param fromAccountNo 转出账户
     * @param toAccountNo   转入账户
     * @param amount        转账的金额
     */
    @RequestMapping("/transfer")
    public Boolean transfer(@RequestParam("fromAccountNo") String fromAccountNo, @RequestParam("toAccountNo") String toAccountNo, @RequestParam("amount") Double amount) {
        accountService.transfer(fromAccountNo, toAccountNo, amount);
        return true;
    }

}