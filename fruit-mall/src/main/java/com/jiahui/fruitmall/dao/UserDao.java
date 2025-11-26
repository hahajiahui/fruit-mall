package com.jiahui.fruitmall.dao;

import com.jiahui.fruitmall.dto.UserRegisterRequest;
import com.jiahui.fruitmall.mode.User;

public interface UserDao {

    Integer creatUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
