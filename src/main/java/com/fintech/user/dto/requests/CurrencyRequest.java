package com.fintech.user.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record CurrencyRequest(
  Long id ,
  String currencyName
) {}
