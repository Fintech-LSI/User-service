package com.fintech.user.config.exception;

import jakarta.validation.constraints.Email;

public class EmailAlreadyExistsException extends RuntimeException {
  public EmailAlreadyExistsException(@Email(message = "Email should be valid") @Email(message = "Email should be valid") String s) {
  }
}
