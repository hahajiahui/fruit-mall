package com.jiahui.fruitmall.service;

//import com.jiahui.fruitmall.constant.ProductCategory;
import com.jiahui.fruitmall.dto.ProductQueryPararm;
import com.jiahui.fruitmall.dto.ProductRequest;
import com.jiahui.fruitmall.mode.Product;

import java.util.List;

public interface ProductServer {

    List<Product> getProducts(ProductQueryPararm productQueryPararm);
    //List<Product> getProducts(ProductCategory category,String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProductById(Integer productId);


}
