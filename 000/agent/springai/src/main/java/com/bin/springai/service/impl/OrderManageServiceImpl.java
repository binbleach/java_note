package com.bin.springai.service.impl;

import com.bin.springai.service.OrderManageService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderManageServiceImpl implements OrderManageService {
    @Override
    public String getOrderById(String orderId) {
        return "订单号：" + orderId;
    }

    @Override
    public String refund(String orderId, String reason) {
        System.out.println("退款成功");
        return UUID.randomUUID().toString();
    }
}
