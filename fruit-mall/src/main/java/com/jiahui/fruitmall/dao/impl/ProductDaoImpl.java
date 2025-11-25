package com.jiahui.fruitmall.dao.impl;

//import com.jiahui.fruitmall.constant.ProductCategory;
import com.jiahui.fruitmall.dao.ProductDao;
import com.jiahui.fruitmall.dto.ProductQueryPararm;
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
    public List<Product> getProducts(ProductQueryPararm productQueryPararm) {

        //sql 語法 查商品 列表  WHERE 1=1
        String  sql="SELECT  product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date "+
                " FROM product WHERE 1=1";



        //map 裝前端傳來參數值
        Map<String,Object> map =new HashMap<>();


//        if(productQueryPararm.getCategory()!=null) {
//
//            sql = sql + " AND category=:category";
//
//            map.put("category", productQueryPararm.getCategory().name());
//
//        }
//
//        //注意 模糊查詢 srping boot不是直覺寫 sql 要拆開
//        // 先 product_name LIKE :search"  再 "%"+search+"%"
//        if(productQueryPararm.getSearch()!=null){
//            sql=sql+" AND product_name LIKE :search";
//            map.put("search","%"+productQueryPararm.getSearch()+"%");
//        }


        //查詢 條件
        sql=addFilteringsql(sql,map,productQueryPararm);

        sql=sql+" ORDER BY "+ productQueryPararm.getOrderBy()+" "+productQueryPararm.getSort();


        // 分頁  限制查詢筆數 offset
        sql=sql+" LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryPararm.getLimit());
        map.put("offset",productQueryPararm.getOffset());

        //執行sql   找出一列 一列 product 就是一個 一個 product 物件
        //用 list 裝
        List<Product> productList= namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        return productList;
    }

    @Override
    //接前端 傳來 值 存在 一個類別  把類別看成一個 變數 只是這個變數 很大
    public Integer countProduct(ProductQueryPararm productQueryPararm) {

        String sql= "SELECT COUNT(*) FROM product WHERE 1=1";


        //map 裝前端傳來參數值
        Map<String,Object> map =new HashMap<>();



//        if(productQueryPararm.getCategory()!=null) {
//
//            sql = sql + " AND category=:category";
//
//            map.put("category", productQueryPararm.getCategory().name());
//
//        }
//
//        //注意 模糊查詢 srping boot不是直覺寫 sql 要拆開
//        // 先 product_name LIKE :search"  再 "%"+search+"%"
//        if(productQueryPararm.getSearch()!=null){
//            sql=sql+" AND product_name LIKE :search";
//            map.put("search","%"+productQueryPararm.getSearch()+"%");
//        }



        //查詢 條件
        sql=addFilteringsql(sql,map,productQueryPararm);


         Integer total =namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return total;
    }

    //    @Override
//    public List<Product> getProducts(ProductCategory category,String search) {
//
//        //sql 語法 查商品 列表  WHERE 1=1
//        String  sql="SELECT  product_id, product_name, category, image_url, price, stock, description, " +
//                "created_date, last_modified_date "+
//                " FROM product WHERE 1=1";
//
//
//
//        //map 裝前端傳來參數值
//        Map<String,Object> map =new HashMap<>();
//
//
//        if(category!=null) {
//
//            sql = sql + " AND category=:category";
//
//            map.put("category", category.name());
//
//        }
//
//        //注意 模糊查詢 srping boot不是直覺寫 sql 要拆開
//        // 先 product_name LIKE :search"  再 "%"+search+"%"
//        if(search!=null){
//            sql=sql+" AND product_name LIKE :search";
//            map.put("search","%"+search+"%");
//        }
//
//
//        //執行sql   找出一列 一列 product 就是一個 一個 product 物件
//        //用 list 裝
//        List<Product> productList= namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());
//
//        return productList;
//    }



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


       //回傳物件 轉為 int
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


    //重覆程式碼 整理在一起
    //查詢 條件

    private String addFilteringsql(String sql,Map<String,Object>map,ProductQueryPararm productQueryPararm){


        if(productQueryPararm.getCategory()!=null) {

            sql = sql + " AND category=:category";

            map.put("category", productQueryPararm.getCategory().name());

        }

        //注意 模糊查詢 srping boot不是直覺寫 sql 要拆開
        // 先 product_name LIKE :search"  再 "%"+search+"%"
        if(productQueryPararm.getSearch()!=null){
            sql=sql+" AND product_name LIKE :search";
            map.put("search","%"+productQueryPararm.getSearch()+"%");
        }

         return sql;
    }


}
