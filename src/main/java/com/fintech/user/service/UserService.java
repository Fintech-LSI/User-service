package com.fintech.user.service;

import com.fintech.user.config.exception.UserNotFoundException;
import com.fintech.user.controller.dto.requests.UserRequest;
import com.fintech.user.entity.User;
import com.fintech.user.repository.UserRepository;
import com.fintech.user.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
  }

  public User updateUser(Long id, UserRequest userRequest) {
    User existingUser = getUserById(id);
    existingUser.setFirstName(userRequest.firstName());
    existingUser.setLastName(userRequest.lastName());
    existingUser.setEmail(userRequest.email());
    return userRepository.save(existingUser);
  }

  public void deleteUser(Long id) {
    if (!isUserExist(id)) {
      throw new UserNotFoundException(String.valueOf(id));
    }
    userRepository.deleteById(id);
  }

}
