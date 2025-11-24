package com.jiahui.fruitmall.service;

import com.jiahui.fruitmall.dto.ProductRequest;
import com.jiahui.fruitmall.mode.Product;

public interface ProductServer {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
