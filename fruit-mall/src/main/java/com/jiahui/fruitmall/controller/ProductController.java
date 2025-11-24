package com.jiahui.fruitmall.controller;


import com.jiahui.fruitmall.dto.ProductRequest;
import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.service.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


    @PostMapping("/product")
   public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId =productServer.createProduct(productRequest);

        Product product =productServer.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);

   }


   @PutMapping("/product/{productId}")
   public ResponseEntity<Product> updateProduct(  @PathVariable Integer productId,
                                                  @RequestBody @Valid ProductRequest productRequest){

        //檢查商品是否存在
       Product product =productServer.getProductById(productId);

       if(product==null){
           return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }

       //有這商品
        productServer.updateProduct(productId,productRequest);

        Product updateProduct =productServer.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);

   }


}
