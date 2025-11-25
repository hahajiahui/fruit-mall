package com.jiahui.fruitmall.service;

import com.jiahui.fruitmall.dto.UserRegisterRequest;
import com.jiahui.fruitmall.mode.User;

public interface UserService {

    public Integer register(UserRegisterRequest userRegisterRequest);
    public User getUserById(Integer userId);

}
