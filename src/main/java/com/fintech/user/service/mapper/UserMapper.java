package com.fintech.user.service.mapper;

import com.fintech.user.controller.dto.requests.UserRequest;
import com.fintech.user.controller.dto.responses.UserResponse;
import com.fintech.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
 public User requestToUser(UserRequest userRequest) {
     return User.builder()
       .id(userRequest.id())
       .email(userRequest.email())
       .firstName(userRequest.firstName())
       .lastName(userRequest.lastName())
       .build();
  }


  public UserResponse userToResponse(User user) {
    return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail() , user.getAge(),user.getSalary(),user.getHomeOwnership(),user.getEmploymentMonth(),user.getImage().getUrl());
  }
}
