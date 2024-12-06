package com.fintech.user.service;

import com.fintech.user.controller.dto.requests.UserRequest;
import com.fintech.user.entity.User;
import com.fintech.user.repository.UserRepository;
import com.fintech.user.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  UserMapper userMapper;

  public User saveUser(UserRequest userRequest) {
    User user = userMapper.requestToUser(userRequest);
    return userRepository.save(user);
  }
  public Boolean isUserExist(Long id) {
    return userRepository.existsById(id);
  }
}
