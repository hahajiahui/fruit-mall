package com.jiahui.fruitmall.controller;

import com.jiahui.fruitmall.dto.CreateOrderRequest;
import com.jiahui.fruitmall.mode.Order;
import com.jiahui.fruitmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class OrderController {
    @Autowired
   private OrderService orderService;

   //創訂單
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOder(@PathVariable Integer userId,
                                        @RequestBody @Valid CreateOrderRequest creatOrderRequest){

         Integer orderId= orderService.createOrder(userId,creatOrderRequest);


         Order order = orderService.getOrderById(orderId);

         return  ResponseEntity.status(HttpStatus.OK).body(order);

    }



}
