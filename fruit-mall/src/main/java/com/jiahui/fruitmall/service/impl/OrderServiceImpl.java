package com.jiahui.fruitmall.service.impl;

import com.jiahui.fruitmall.dao.OrderDao;
import com.jiahui.fruitmall.dao.ProductDao;
import com.jiahui.fruitmall.dto.BuyItem;
import com.jiahui.fruitmall.dto.CreateOrderRequest;
import com.jiahui.fruitmall.dto.OrderQueryParams;
import com.jiahui.fruitmall.mode.Order;
import com.jiahui.fruitmall.mode.OrderItem;
import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.mode.User;
import com.jiahui.fruitmall.service.OrderService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private  OrderDao orderDao;
    @Autowired
    private ProductDao productDao;


    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);



    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {

        List<Order>orderList =orderDao.getOrders(orderQueryParams);

        //每 個 訂單 詳細訂單資料
        for(Order order :orderList){
            List<OrderItem> orderItemList=orderDao.getOrderItemsByOrderId(order.getOrderId());

            order.setOrderItemList(orderItemList);
        }
        return orderList;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {



       //檢查傳來 userId 是否存在

       User user = new User();

       if(user==null){

           log.warn("該 帳號 {} 不存在",userId);

           throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
       }

        int totalAmount=0;

        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
           //?? 配 table
            Product product = productDao.getProductById(buyItem.getProductId());

            //檢查商品是否存在 庫存是否夠

            if(product==null){
                log.warn("這商品{} 不存在 ",buyItem.getProductId());

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
             }else if(product.getStock()<buyItem.getQuantity()){

                log.warn("商品 {}庫存數不足， 無法購買 剩餘庫存{}，欲購買數量{}",
                        buyItem.getProductId(),product.getStock(),buyItem.getQuantity());

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
             }

            //扣除商品庫存

            productDao.updateStock(product.getProductId(),product.getStock()-buyItem.getQuantity());





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
