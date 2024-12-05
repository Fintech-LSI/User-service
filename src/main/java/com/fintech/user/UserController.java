package com.fintech.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  // Test endpoint
  @GetMapping("/test")
  public String test() {
    return "User service is running!";
  }

  // Create a new user
  @PostMapping
  public User createUser(@RequestBody User user) {
    User u = new User();
    u.setName(user.getName());
    u.setEmail(user.getEmail());

    return userRepository.save(u);
  }

  // Get all users
  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  // Delete a user by ID
  @DeleteMapping("/{id}")
  public String deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
    return "User with ID " + id + " has been deleted.";
  }
}
