package com.jiahui.fruitmall.dao;

import com.jiahui.fruitmall.dto.ProductRequest;
import com.jiahui.fruitmall.mode.Product;

import java.util.List;


public interface ProductDao {

    List<Product> getProducts();

    Product getProductById(Integer productId);


    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);












































}
