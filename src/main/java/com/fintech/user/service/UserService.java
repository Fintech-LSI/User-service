package com.fintech.user.service;

import com.fintech.user.config.exception.UserNotFoundException;
import com.fintech.user.controller.dto.requests.UserRequest;
import com.fintech.user.entity.Image;
import com.fintech.user.entity.User;
import com.fintech.user.repository.UserRepository;
import com.fintech.user.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private ImageService imageService;

  public User saveUser(UserRequest userRequest) throws IOException {
    User user = userMapper.requestToUser(userRequest);

    if (userRequest.imageFile() != null && !userRequest.imageFile().isEmpty()) {
      // Save the uploaded image
      Image uploadedImage = imageService.saveImage(userRequest.imageFile());
      user.setImage(uploadedImage);
    } else if (user.getImage() == null) {
      // Assign default image if no image is provided
      Image defaultImage = imageService.getDefaultImage();
      user.setImage(defaultImage);
    }

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

  public User updateUser(Long id, UserRequest userRequest) throws IOException {
    User existingUser = getUserById(id);
    existingUser.setFirstName(userRequest.firstName());
    existingUser.setLastName(userRequest.lastName());
    existingUser.setEmail(userRequest.email());
    existingUser.setAge(userRequest.age());

    if (userRequest.imageFile() != null && !userRequest.imageFile().isEmpty()) {
      // Replace the old image with the new one
      if (existingUser.getImage() != null) {
        imageService.deleteImage(existingUser.getImage().getId());
      }
      Image uploadedImage = imageService.saveImage(userRequest.imageFile());
      existingUser.setImage(uploadedImage);
    }

    return userRepository.save(existingUser);
  }

  public void deleteUser(Long id) {
    if (!isUserExist(id)) {
      throw new UserNotFoundException(String.valueOf(id));
    }
    userRepository.deleteById(id);
  }

  public Optional<User> findByEmail(String email) {
    return Optional.ofNullable(userRepository.findByEmail(email));
  }

}
