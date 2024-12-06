package com.fintech.user.config.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String id) {
    super("user not found :: with ID "+id);
  }
}
