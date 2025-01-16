package com.fintech.user.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponse {
  private Long id;
  private String name;
  private String code;
  private Double exchangeRate;
}
