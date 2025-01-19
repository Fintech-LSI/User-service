package com.fintech.user.service.mapper;

import com.fintech.user.dto.requests.UserRequest;
import com.fintech.user.dto.responses.UserResponse;
import com.fintech.user.entity.OwnerShip;
import com.fintech.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
  public User requestToUser(UserRequest userRequest) {
    return User.builder()
      .id(userRequest.id()) // Mandatory field
      .email(userRequest.email()) // Mandatory field
      .firstName(userRequest.firstName()) // Mandatory field
      .lastName(userRequest.lastName()) // Mandatory field
      .age(userRequest.age() != null ? userRequest.age() : null) // Optional field
      .salary(userRequest.salary() != null ? userRequest.salary() : null) // Optional field
      .homeOwnership(userRequest.homeOwnership() != null ? OwnerShip.valueOf( userRequest.homeOwnership() ) : null) // Optional field
      .employmentMonth(userRequest.employmentMonth() != null ? userRequest.employmentMonth() : null) // Optional field
      .build();
  }



  public UserResponse userToResponse(User user) {
    return UserResponse.builder()
      .id(user.getId() != null ? user.getId() : null)
      .email(user.getEmail()) // Email is mandatory, no null check needed
      .firstName(user.getFirstName()) // First name is mandatory, no null check needed
      .lastName(user.getLastName()) // Last name is mandatory, no null check needed
      .age(user.getAge() != null ? user.getAge() : null)
      .image(user.getImage() != null ? user.getImage().getUrl() : null)
      .salary(user.getSalary() != null ? user.getSalary() : null)
      .employmentMonth(user.getEmploymentMonth() != null ? user.getEmploymentMonth() : null)
      .homeOwnership(user.getHomeOwnership() != null ? user.getHomeOwnership().toString() : null)
//      .isEmailVerified(user.getIsEmailVerified())
      .build();
  }

}
