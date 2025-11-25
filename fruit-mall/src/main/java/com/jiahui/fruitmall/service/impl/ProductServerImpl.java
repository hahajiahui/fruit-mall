package com.jiahui.fruitmall.service.impl;

//import com.jiahui.fruitmall.constant.ProductCategory;
import com.jiahui.fruitmall.dao.ProductDao;
import com.jiahui.fruitmall.dto.ProductQueryPararm;
import com.jiahui.fruitmall.dto.ProductRequest;
import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.service.ProductServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServerImpl implements ProductServer {
    @Autowired
  private ProductDao productDao;



//    @Override
//    public List<Product> getProducts(ProductCategory category,String search) {
//        return productDao.getProducts(category,search);
//    }

    @Override
    public List<Product> getProducts(ProductQueryPararm productQueryPararm) {
        return productDao.getProducts(productQueryPararm);
    }

    @Override
    public Product getProductById(Integer productId) {


        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }


}
