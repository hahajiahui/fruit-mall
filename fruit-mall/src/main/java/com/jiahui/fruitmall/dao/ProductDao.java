package com.jiahui.fruitmall.dao;

import com.jiahui.fruitmall.dto.ProductRequest;
import com.jiahui.fruitmall.mode.Product;


public interface ProductDao {


    Product getProductById(Integer productId);


    Integer createProduct(ProductRequest productRequest);














































}
