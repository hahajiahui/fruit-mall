package com.jiahui.fruitmall.service.impl;

import com.jiahui.fruitmall.dao.OrderDao;
import com.jiahui.fruitmall.dao.ProductDao;
import com.jiahui.fruitmall.dto.BuyItem;
import com.jiahui.fruitmall.dto.CreateOrderRequest;
import com.jiahui.fruitmall.mode.Order;
import com.jiahui.fruitmall.mode.OrderItem;
import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private  OrderDao orderDao;
    @Autowired
    private ProductDao productDao;





   @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {


        int totalAmount=0;

        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
           //?? 配 table
            Product product = productDao.getProductById(buyItem.getProductId());

            //計算總金額
            int amount =buyItem.getQuantity()*product.getProductId();
            totalAmount =totalAmount+amount;


            //轉換 BuyItem to  OrderItem
            OrderItem orderItem = new OrderItem();
            //訂單編號
            orderItem.setOrderItemId(buyItem.getProductId());
            //訂單裡的商品項目
            orderItem.setProductId(buyItem.getProductId());
            //數量
            orderItem.setQuantity((buyItem.getQuantity()));
            //金額
            orderItem.setAmount(amount);
            //加訂單
            orderItemList.add(orderItem);

        }



        //創建 訂單   order 二個欄位 userId  totalAmonut

        Integer orderId= orderDao.createOrder(userId,totalAmount);
        //哪張訂單 訂單項目
        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;

    }


    @Override
    public Order getOrderById(Integer orderId) {

       Order order = orderDao.getOrderById(orderId);

       List<OrderItem>orderItemList =orderDao.getOrderItemsByOrderId(orderId);

       //合並 order orderItem 在 order class裡 增加 List<OrderItem>

        order.setOrderItemList(orderItemList);

        return order;
    }
}
