package com.jiahui.fruitmall.service.impl;

import com.jiahui.fruitmall.dao.ProductDao;
import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.service.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServerImpl implements ProductServer {
    @Autowired
  private ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) {


        return productDao.getProductById(productId);
    }
}
