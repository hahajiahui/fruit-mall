package com.jiahui.fruitmall.dao.impl;

import com.jiahui.fruitmall.dao.ProductDao;
import com.jiahui.fruitmall.dto.ProductRequest;
import com.jiahui.fruitmall.mode.Product;
import com.jiahui.fruitmall.rowapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDaoImpl implements ProductDao {

//查詢 sql

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Product> getProducts() {

        //sql 語法 查商品 列表
        String  sql="SELECT  product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date "+
                " FROM product";


        //map 裝前端傳來參數值
        Map<String,Object> map =new HashMap<>();


        //執行sql   找出一列 一列 product 就是一個 一個 product 物件
        //用 list 裝
        List<Product> productList= namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());



        return productList;
    }

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

    @Override
    public Integer createProduct(ProductRequest productRequest) {


        //SQL 新增語法
      String sql="INSERT INTO product (product_name, category, image_url, price, stock," +
                " description, created_date, last_modified_date)" +
                " VALUES (:productName,:category,:imageUrl ,:price,:stock, :description," +
                ":createDate,:lastModifiedDate)";


        //map 裝前端傳來 資料
        Map<String,Object> map=new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date now = new Date();

        map.put("createDate",now);
        map.put("lastModifiedDate",now);

       //存 資料庫自動生 成 productid
        KeyHolder keyHolder=new GeneratedKeyHolder();

        //到資料庫查資料 語法

       namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);


       //??
       int productid =keyHolder.getKey().intValue();


        return productid;

    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        //SQL 更新語法
        String sql="Update product set product_name=:productName,category=:category,image_url=:imageUrl,"+
                "price=:price,stock=:stock,description=:description,last_modified_date=:lastModifiedDate"+
                " WHERE product_id=:productId";

        // map 裝 要查詢的 sql 語法 接前端傳來 json 數值

        Map<String,Object> map =new HashMap<>();

        map.put("productId",productId);

        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());


        map.put("lastModifiedDate",new Date());

        // 到資料庫 執行sql
        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void deleteProductById(Integer productId) {

        String sql= "DELETE FROM product WHERE product_id=:productId";

        Map<String,Object>map=new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);

    }


}
