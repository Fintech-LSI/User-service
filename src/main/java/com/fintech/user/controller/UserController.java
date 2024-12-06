package com.fintech.user.controller;

import com.fintech.user.config.exception.UserAlreadyExist;
import com.fintech.user.controller.dto.requests.UserRequest;
import com.fintech.user.repository.UserRepository;
import com.fintech.user.entity.User;
import com.fintech.user.service.UserService;
import com.fintech.user.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  // Test endpoint
  @GetMapping("/test")
  public String test() {
    User user = new User();
    user.setName("test");
    user.setEmail("test@test.com");
    return "User service is running! "+user.getEmail()+" "+user.getName();
  }


  // Create a new user
  @PostMapping
  public User createUser(@RequestBody UserRequest request) {
    if (request.id()!=null && userService.isUserExist(request.id())){
      throw new UserAlreadyExist(String.valueOf(request.id()) );
    }
    return userService.saveUser(request);
  }


}
