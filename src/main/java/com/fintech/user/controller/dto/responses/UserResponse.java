package com.fintech.user.controller.dto.responses;

import com.fintech.user.entity.OwnerShip;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

public record UserResponse(
  Long id,
  @NotNull()
  @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
  String firstName,

  @NotNull()
  @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
  String lastName,

  @NotNull()
  @Email(message = "Email should be valid")
  String email,

  Integer age ,
  Double salary,
  OwnerShip homeOwnership,
  Integer employmentMonth,

  String image
) {}
