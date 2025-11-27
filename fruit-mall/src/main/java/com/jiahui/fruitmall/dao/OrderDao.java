package com.jiahui.fruitmall.dao;

import com.jiahui.fruitmall.mode.Order;
import com.jiahui.fruitmall.mode.OrderItem;

import java.util.List;

public interface OrderDao {

   Integer createOrder(Integer userId,Integer totalAmount);
   void  createOrderItems(Integer orderId, List<OrderItem> orderItemList);


    Order getOrderById(Integer orderId);

    //訂單裡會有很多商品 用list 把這一列一列商品 裝起
  List<OrderItem>  getOrderItemsByOrderId(Integer orderId);

}
