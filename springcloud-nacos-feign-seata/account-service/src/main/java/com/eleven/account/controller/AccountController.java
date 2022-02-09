package com.eleven.account.controller;

import com.eleven.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @RequestMapping("/debit")
    public Boolean debit(String userId, int money) throws Exception {
        // 用户账户扣款
        accountService.debit(userId, money);
        return true;
    }
    
}
