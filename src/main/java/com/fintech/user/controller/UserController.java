package com.fintech.user.controller;

import com.fintech.user.config.exception.UserAlreadyExist;
import com.fintech.user.controller.dto.requests.UserRequest;
import com.fintech.user.controller.dto.responses.UserResponse;
import com.fintech.user.entity.Image;
import com.fintech.user.entity.User;
import com.fintech.user.service.ImageService;
import com.fintech.user.service.UserService;
import com.fintech.user.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserMapper userMapper;

  // Test endpoint
  @GetMapping("/test")
  public String test() {
    User user = new User();
    user.setFirstName("test");
    user.setLastName("test");
    user.setEmail("test@test.com");
    return "User service is running! "+user.getEmail()+" "+user.getFirstName()+" "+user.getLastName();
  }
  // Test endpoint
  @PostMapping("/image/test")
  public String testimage(@ModelAttribute UserRequest ur)  {
    Image i;
    try{
        i = imageService.saveImage(ur.imageFile());
    } catch (IOException e) {
      return e.getMessage();
    }

   return i.getUrl();
  }

  // Create a new user
  @PostMapping
  public ResponseEntity<UserResponse> createUser(@ModelAttribute UserRequest request) throws IOException {
    if (request.id() != null && userService.isUserExist(request.id())) {
      throw new UserAlreadyExist(String.valueOf(request.id()));
    }
    User createdUser = userService.saveUser(request);
    return ResponseEntity.ok(userMapper.userToResponse(createdUser));
  }
  // Get all users
  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users.stream().map(userMapper::userToResponse).toList());
  }
  // Get user by ID
  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
    User user = userService.getUserById(id);
    return ResponseEntity.ok(userMapper.userToResponse(user));
  }
  // Update user
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) throws IOException {
    User updatedUser = userService.updateUser(id, request);
    return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
  }

  // Delete user
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok("User deleted successfully");
  }

}
