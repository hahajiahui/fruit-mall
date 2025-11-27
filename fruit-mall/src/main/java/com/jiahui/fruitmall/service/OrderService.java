package com.jiahui.fruitmall.service;

import com.jiahui.fruitmall.dto.CreateOrderRequest;
import com.jiahui.fruitmall.dto.OrderQueryParams;
import com.jiahui.fruitmall.mode.Order;

import java.util.List;

public interface OrderService {

   Integer countOrder(OrderQueryParams orderQueryParams);

   List<Order> getOrders(OrderQueryParams orderQueryParams);

   Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
   Order getOrderById(Integer orderId);
}
