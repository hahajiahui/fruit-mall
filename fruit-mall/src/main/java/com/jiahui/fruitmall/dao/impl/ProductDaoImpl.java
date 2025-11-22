package com.jiahui.fruitmall.dao.impl;

import com.jiahui.fruitmall.dao.ProductDao;
import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.rowapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

//查詢 sql

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Product getProductById(Integer productId) {

        String sql="SELECT product_id,product_name, category, image_url, price, stock, description," +
                "created_date, last_modified_date " +
                "FROM product WHERE product_id=:productId";


        Map<String,Object> map=new HashMap<>();
        map.put("productId",productId);


        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size()>0){
            return  productList.get(0);
        }else{
            return null;
        }
    }
}
