package com.fintech.user.service;

import com.fintech.user.config.exception.EmailAlreadyExistsException;
import com.fintech.user.config.exception.UserNotFoundException;
import com.fintech.user.controller.dto.requests.UserRequest;
import com.fintech.user.entity.Image;
import com.fintech.user.entity.OwnerShip;
import com.fintech.user.entity.User;
import com.fintech.user.repository.UserRepository;
import com.fintech.user.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
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

    // Only update the email if it has changed
    if (userRequest.email() != null && !userRequest.email().equals(existingUser.getEmail())) {
      if (userRepository.existsByEmailAndIdNot(userRequest.email(), id)) {
        throw new EmailAlreadyExistsException("Email " + userRequest.email() + " is already in use");
      }
      existingUser.setEmail(userRequest.email());
    }

    // Update fields only if they have changed
    if (userRequest.firstName() != null && !userRequest.firstName().equals(existingUser.getFirstName())) {
      existingUser.setFirstName(userRequest.firstName());
    }

    if (userRequest.lastName() != null && !userRequest.lastName().equals(existingUser.getLastName())) {
      existingUser.setLastName(userRequest.lastName());
    }

    if (userRequest.age() != null && !userRequest.age().equals(existingUser.getAge())) {
      existingUser.setAge(userRequest.age());
    }

    if (userRequest.salary() != null && !userRequest.salary().equals(existingUser.getSalary())) {
      existingUser.setSalary(userRequest.salary());
    }

    if (userRequest.homeOwnership() != null && !userRequest.homeOwnership().equals(existingUser.getHomeOwnership())) {
      existingUser.setHomeOwnership(OwnerShip.valueOf(userRequest.homeOwnership()));
    }

    if (userRequest.employmentMonth() != null && !userRequest.employmentMonth().equals(existingUser.getEmploymentMonth())) {
      existingUser.setEmploymentMonth(userRequest.employmentMonth());
    }

    // Handle image replacement
    if (userRequest.imageFile() != null && !userRequest.imageFile().isEmpty()) {
      // Store the old image reference
      Image oldImage = existingUser.getImage();

      // First save the new image
      Image uploadedImage = imageService.saveImage(userRequest.imageFile());

      // Update user's image reference
      existingUser.setImage(uploadedImage);

      // Save the user with new image
      User savedUser = userRepository.save(existingUser);

      // Now it's safe to delete the old image
      if (oldImage != null && !oldImage.getUrl().equals("default_profile_picture.png")) {
        try {
          imageService.deleteImage(oldImage.getId());
        } catch (Exception e) {
          // Log the error but don't fail the update
          log.error("Failed to delete old image: " + e.getMessage());
        }
      }

      return savedUser;
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
