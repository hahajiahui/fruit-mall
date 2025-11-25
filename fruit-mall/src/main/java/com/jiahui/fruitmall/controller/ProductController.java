package com.jiahui.fruitmall.controller;


import com.jiahui.fruitmall.constant.ProductCategory;
import com.jiahui.fruitmall.dto.ProductQueryPararm;
import com.jiahui.fruitmall.dto.ProductRequest;
import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.service.ProductServer;
import com.jiahui.fruitmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
   private ProductServer productServer;


    //查詢全部商品
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(//查詢條件 filtering
                                                     @RequestParam (required = false) ProductCategory category,
                                                     @RequestParam (required=false) String search,
                                                     //排序
                                                     @RequestParam(defaultValue = "created_date") String orderBy,
                                                     @RequestParam (defaultValue = "desc")String sort,
                                                     @RequestParam (defaultValue = "5") @Max(1000) @Min(0) Integer limit,
                                                     @RequestParam (defaultValue = "0")@Min(0)Integer offset
    ){

        ProductQueryPararm productQueryPararm = new ProductQueryPararm();
        productQueryPararm.setCategory(category);
        productQueryPararm.setSearch(search);
        productQueryPararm.setOrderBy(orderBy);
        productQueryPararm.setSort(sort);
        productQueryPararm.setLimit(limit);
        productQueryPararm.setOffset(offset);


        List<Product>proudctList =productServer.getProducts(productQueryPararm);

       //計算查到的 有多少筆
        Integer total =productServer.countProduct(productQueryPararm);




        //分頁
        Page<Product>page= new Page<Product>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(proudctList);

        return ResponseEntity.status(HttpStatus.OK).body(page);

    }


    //查詢 商品列表

//    @GetMapping("/products")
//    public ResponseEntity<List<Product>> getProducts(//查詢條件 filtering
//                                                     @RequestParam (required = false) ProductCategory category,
//                                                     @RequestParam (required=false) String search,
//                                                     //排序
//                                                     @RequestParam(defaultValue = "created_date") String orderBy,
//                                                     @RequestParam (defaultValue = "desc")String sort,
//                                                     @RequestParam (defaultValue = "5") @Max(1000) @Min(0) Integer limit,
//                                                     @RequestParam (defaultValue = "0")@Min(0)Integer offset
//    ){
//
//        ProductQueryPararm productQueryPararm = new ProductQueryPararm();
//        productQueryPararm.setCategory(category);
//        productQueryPararm.setSearch(search);
//        productQueryPararm.setOrderBy(orderBy);
//        productQueryPararm.setSort(sort);
//        productQueryPararm.setLimit(limit);
//        productQueryPararm.setOffset(offset);
//
//
//        List<Product>proudctList =productServer.getProducts(productQueryPararm);
//
//        return ResponseEntity.status(HttpStatus.OK).body(proudctList);
//
//    }

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

   @DeleteMapping("/product/{productId}")
   public ResponseEntity<Product> delectProduct( @PathVariable Integer productId){
        productServer.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

   }


}
