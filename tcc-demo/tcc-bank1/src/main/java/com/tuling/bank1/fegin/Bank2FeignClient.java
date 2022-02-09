package com.tuling.bank1.fegin;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="tcc-bank2",fallback=Bank2FeignClientFallback.class)
public interface Bank2FeignClient {
    
    @GetMapping("/bank2/transferTo")
    @Hmily
    public  Boolean transferTo(@RequestParam("accountNo") String accountNo,@RequestParam("amount") Double amount);
}