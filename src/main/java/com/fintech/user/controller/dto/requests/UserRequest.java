package com.fintech.user.controller.dto.requests;

public record UserRequest(
  Long id ,
  String firstName ,
  String lastName ,
  String email
) {}
