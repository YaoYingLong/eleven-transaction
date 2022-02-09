package com.tuling.bank1.fegin;

import org.springframework.stereotype.Component;

@Component
public class Bank2FeignClientFallback implements Bank2FeignClient {

    @Override
    public Boolean transferTo(String accountNo,Double amount) {
        // 调用失败降级处理
        return false;
    }
}