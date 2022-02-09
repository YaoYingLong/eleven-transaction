package com.tuling.bank1.service.impl;

import com.tuling.bank1.common.TransactionEnum;
import com.tuling.bank1.fegin.Bank2FeignClient;
import com.tuling.bank1.mapper.AccountMapper;
import com.tuling.bank1.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    Bank2FeignClient bank2FeignClient;

    /**
     * try方法执行逻辑：
     * 1.try幂等校验
     * 2.try悬挂处理
     * 3.检查余额是否足够扣减
     * 4.扣减金额
     *
     * @param fromAccountNo 转出账户
     * @param toAccountNo   转入账户
     * @param amount        转账的金额
     */
    // 只要标记@Hmily就是try方法，在注解中指定confirm、cancel两个方法的名字
    @Transactional
    @Hmily(confirmMethod = "commit", cancelMethod = "rollback")
    @Override
    public void transfer(String fromAccountNo, String toAccountNo, Double amount) {
        // 获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank1 try begin 开始执行...xid:{}", transId);
        // 幂等判断 判断local_transaction_log表中是否有try日志记录，如果有则不再执行
        if (accountMapper.isExistTransactionLogByType(transId, TransactionEnum.TRY.getValue()) > 0) {
            log.info("bank1 try 已经执行，无需重复执行,xid:{}", transId);
            return;
        }
        // try悬挂处理，如果cancel、confirm有一个已经执行了，try不再执行
        if (accountMapper.isExistTransactionLogByType(transId, TransactionEnum.CONFIRM.getValue()) > 0
                || accountMapper.isExistTransactionLogByType(transId, TransactionEnum.CANCEL.getValue()) > 0) {
            log.info("bank1 try悬挂处理  cancel或confirm已经执行，不允许执行try,xid:{}", transId);
            return;
        }
        // 扣减金额
        if (accountMapper.subtractAccountBalance(fromAccountNo, amount) <= 0) {
            // 扣减失败
            throw new RuntimeException("bank1 try 扣减金额失败,xid:" + transId);
        }
        // 插入try执行记录,用于幂等判断
        accountMapper.addTransactionLog(transId, TransactionEnum.TRY.getValue());
        // 转账,远程调用bank2
        if (!bank2FeignClient.transferTo(toAccountNo, amount)) {
            throw new RuntimeException("bank1 远程调用bank2微服务失败,xid:" + transId);
        }
        if (amount == 20) {
            throw new RuntimeException("人为制造异常,xid:" + transId);
        }
        log.info("bank1 try end 结束执行...xid:{}", transId);
    }

    @Transactional
    public void commit(String fromAccountNo, String toAccountNo, Double amount) {
        // 获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank1 confirm begin 开始执行...xid:{},accountNo:{},amount:{}", transId, fromAccountNo, amount);
    }


    /**
     * cancel方法执行逻辑： 1.cancel幂等校验 2.cancel空回滚处理 3.增加可用余额
     */
    @Transactional
    public void rollback(String fromAccountNo, String toAccountNo, Double amount) {
        // 获取全局事务id
        String transId = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("bank1 cancel begin 开始执行...xid:{}", transId);
        //	cancel幂等校验
        if (accountMapper.isExistTransactionLogByType(transId, TransactionEnum.CANCEL.getValue()) > 0) {
            log.info("bank1 cancel 已经执行，无需重复执行,xid:{}", transId);
            return;
        }
        // cancel空回滚处理，如果try没有执行，cancel不允许执行
        if (accountMapper.isExistTransactionLogByType(transId, TransactionEnum.TRY.getValue()) <= 0) {
            log.info("bank1 空回滚处理，try没有执行，不允许cancel执行,xid:{}", transId);
            return;
        }
        //	增加可用余额
        accountMapper.addAccountBalance(fromAccountNo, amount);
        //插入一条cancel的执行记录
        accountMapper.addTransactionLog(transId, TransactionEnum.CANCEL.getValue());
        log.info("bank1 cancel end 结束执行...xid:{}", transId);
    }
}
