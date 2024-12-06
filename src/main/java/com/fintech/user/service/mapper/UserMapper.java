package com.fintech.user.service.mapper;

import com.fintech.user.controller.dto.requests.UserRequest;
import com.fintech.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
  public User requestToUser(UserRequest userRequest) {
     return User.builder()
       .id(userRequest.id())
       .email(userRequest.email())
       .name(userRequest.firstName() + " " + userRequest.lastName())
       .build();
  }

}
