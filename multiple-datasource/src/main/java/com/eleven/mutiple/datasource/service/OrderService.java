package com.eleven.mutiple.datasource.service;


import com.eleven.mutiple.datasource.entity.Order;
import com.eleven.mutiple.datasource.vo.OrderVo;

public interface OrderService {

    /**
     * 保存订单
     */
    Order saveOrder(OrderVo orderVo);
}