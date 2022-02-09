package com.eleven.order.service;

import com.eleven.datasource.entity.Order;
import com.eleven.order.vo.OrderVo;
import io.seata.core.exception.TransactionException;

public interface OrderService {

    /**
     * 保存订单
     */
    Order saveOrder(OrderVo orderVo) throws TransactionException;
}