package com.bin.springai.service;

public interface OrderManageService {

    String getOrderById(String orderId);

    String refund(String orderId, String reason);
}
