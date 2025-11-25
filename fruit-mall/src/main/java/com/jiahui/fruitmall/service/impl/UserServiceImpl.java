package com.jiahui.fruitmall.service.impl;

import com.jiahui.fruitmall.dao.UserDao;
import com.jiahui.fruitmall.dto.UserRegisterRequest;
import com.jiahui.fruitmall.mode.User;
import com.jiahui.fruitmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.creatUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
