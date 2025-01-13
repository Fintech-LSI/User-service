package com.fintech.user.controller.dto.responses;

import com.fintech.user.entity.OwnerShip;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

  private Long id;

  @NotNull
  @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
  private String firstName;

  @NotNull
  @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
  private String lastName;

  @NotNull
  @Email(message = "Email should be valid")
  private String email;

  private String image;
  private Integer age;
  private Double salary;
  private String homeOwnership;
  private Integer employmentMonth;
}
