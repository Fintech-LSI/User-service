package com.fintech.user.controller;

import com.fintech.user.config.exception.UserAlreadyExist;
import com.fintech.user.dto.requests.UserRequest;
import com.fintech.user.dto.responses.MessageResponse;
import com.fintech.user.dto.responses.UserResponse;
import com.fintech.user.entity.User;
import com.fintech.user.service.EmailService;
import com.fintech.user.service.ImageService;
import com.fintech.user.service.UserService;
import com.fintech.user.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private EmailService emailService;


  // Test endpoint
  @GetMapping("/test")
  public String test() {
    emailService.sendVerificationEmail("khalil.hessein@gmail.com","564GGKHALIL");
    return "User service is running! ";
  }

//  @PostMapping("/verify/send")
//  public ResponseEntity<MessageResponse> sendVerificationCode(@RequestParam String email) {
//    userService.sendVerificationCode(email);
//    return ResponseEntity.ok(MessageResponse.builder()
//      .message("Verification code sent successfully")
//      .build());
//  }

//  @PostMapping("/verify")
//  public ResponseEntity<MessageResponse> verifyEmail(
//    @RequestParam String email,
//    @RequestParam String code
//  ){
//    boolean verified = userService.verifyEmail(email, code);
//    if (verified) {
//      return ResponseEntity.ok(MessageResponse.builder()
//        .message("Email verified successfully")
//        .build());
//    } else {
//      return ResponseEntity.badRequest().body(MessageResponse.builder()
//        .message("Invalid or expired verification code")
//        .build());
//    }
//  }




  // Create a new user
  @PostMapping
  public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) throws IOException {
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

    return ResponseEntity.ok(userService.getUserById(id));
  }
  // Update user
  @PutMapping("/{id}")
  public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) throws IOException {
    User updatedUser = userService.updateUser(id, request);
    return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UserResponse> updateImage(@PathVariable Long id, @ModelAttribute UserRequest request) throws IOException {
    User updatedUser = userService.updateUser(id, request);
    return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
  }

  // Delete user
  @DeleteMapping("/{id}")
  public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.ok(MessageResponse.builder().message("User deleted successfully").build());
  }


  @GetMapping("/email/{email}")
  public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
    try {
      // Validate input
      if (email == null || email.trim().isEmpty()) {
        return ResponseEntity.badRequest().body(
          MessageResponse.builder().message("Email cannot be null or empty.").build()
        );
      }

      // Find user by email
      Optional<User> user = userService.findByEmail(email);

      // Check if the user exists
      if (user.isPresent()) {
        UserResponse userResponse = userMapper.userToResponse(user.get()); // Assuming UserResponse is a DTO
        return ResponseEntity.ok(userResponse);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(MessageResponse.builder().message("User with email " + email + " not found.").build());
      }
    } catch (Exception e) {
      // Handle unexpected errors
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(MessageResponse.builder().message("An error occurred while processing the request: " + e.getMessage()).build());
    }
  }

}
