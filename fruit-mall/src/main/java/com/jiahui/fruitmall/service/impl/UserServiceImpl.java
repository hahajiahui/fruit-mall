package com.jiahui.fruitmall.service.impl;

import com.jiahui.fruitmall.dao.UserDao;
import com.jiahui.fruitmall.dto.UserLoginRequest;
import com.jiahui.fruitmall.dto.UserRegisterRequest;
import com.jiahui.fruitmall.mode.User;
import com.jiahui.fruitmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {


    private final static Logger log =LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        //註冊過的 e-mail  就不必再註冊
        //接前端 傳進來 email 傳到 後端 去 查 user table 有沒這 email

          User user =userDao.getUserByEmail(userRegisterRequest.getEmail());

          // 檢查 註冊帳號
          if(user!=null){

              log.warn("該 email {} 已註冊過了",userRegisterRequest.getEmail());

              throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

          }

          //把註冊的 密碼 用 MD5 生成密碼 雜湊值

           String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
          userRegisterRequest.setPassword(hashedPassword);


             // 創建註冊帳號
              return userDao.creatUser(userRegisterRequest);




    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }


    //檢查 前端傳來 e_mail 是否有註冊過
    @Override
    public User login(UserLoginRequest userLoginRequest) {

        User user =userDao.getUserByEmail(userLoginRequest.getEmail());

        //檢查 user 是否存在
        if(user==null){

            log.warn("這e_mail{}未註冊過",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用 MD5 生成 密碼的雜湊值
        String hashePassword =DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        if(user.getPassword().equals(hashePassword)){
            return user;
        }else{

            log.warn("e_mail{}密碼不正確",userLoginRequest.getEmail());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }


    }
}
