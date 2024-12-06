package com.fintech.user.config.exception;

public class UserAlreadyExist extends RuntimeException {
  public UserAlreadyExist(String id) {
    super("user already exist :: with ID "+id);
  }
}
