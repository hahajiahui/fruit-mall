package com.jiahui.fruitmall.controller;


import com.jiahui.fruitmall.dto.UserRegisterRequest;
import com.jiahui.fruitmall.mode.User;
import com.jiahui.fruitmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;

   @PostMapping("/users/register")
    public ResponseEntity<User> Register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
       Integer userId= userService.register(userRegisterRequest);



       User user = userService.getUserById(userId);

      return ResponseEntity.status(HttpStatus.CREATED).body(user);

   }


}
