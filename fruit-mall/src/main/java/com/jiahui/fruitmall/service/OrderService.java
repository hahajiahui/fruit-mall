package com.jiahui.fruitmall.service;

import com.jiahui.fruitmall.dto.CreateOrderRequest;
import com.jiahui.fruitmall.mode.Order;

public interface OrderService {

   Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
   Order getOrderById(Integer orderId);
}
