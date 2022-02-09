package com.tuling.bank2.controller;

import com.tuling.bank2.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank2")
public class Bank2Controller {
    @Autowired
    AccountService accountService;

    @RequestMapping("/transferTo")
    public Boolean transferTo(@RequestParam("accountNo") String accountNo,@RequestParam("amount") Double amount) {
        accountService.transferTo(accountNo, amount);
        return true;
    }

}