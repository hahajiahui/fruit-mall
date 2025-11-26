package com.jiahui.fruitmall.dao.impl;

import com.jiahui.fruitmall.dao.UserDao;
import com.jiahui.fruitmall.dto.UserRegisterRequest;
import com.jiahui.fruitmall.mode.User;
import com.jiahui.fruitmall.rowapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer creatUser(UserRegisterRequest userRegisterRequest) {


        //SQL語法
        String sql="INSERT INTO user (email,password,created_date,last_modified_date) "+
                   "VALUES (:email,:password,:createdDate,:lastModifiedDate)";


        //map
        Map<String,Object>map= new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());

        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);


        //接MySQL 自動生成的id
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //執行sql
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

       int userId = keyHolder.getKey().intValue();

        return userId;
    }

    @Override
    public User getUserByEmail(String email) {

        String sql="SELECT user_id,email,password,created_date,last_modified_date "+
                "FROM user WHERE email= :email";

        Map<String,Object>map = new HashMap<>();
        map.put("email",email);

        List<User>userList =namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.size()>0){
            return userList.get(0);
        }else{
            return null;
        }

    }

    @Override
    public User getUserById(Integer userId) {

        //sql語法
        String sql="SELECT user_id,email,password,created_date,last_modified_date "+
                "FROM user WHERE user_id= :userId";

        //map
        Map<String,Object>map=new HashMap<>();
        map.put("userId",userId);


        //執行sql
        List<User> userList = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());

        if(userList.size()>0){
            return userList.get(0);
        }else {
            return null;
        }

    }



}
