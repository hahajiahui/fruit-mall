package com.jiahui.fruitmall.controller;


import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.service.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
   private ProductServer productServer;

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable  Integer productId){

        Product product=productServer.getProductById(productId);

        if(product!=null){
          return   ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
          return   ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
