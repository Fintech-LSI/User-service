package com.fintech.user.service.feign_clients;

import com.fintech.user.dto.responses.CurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "wallet-service", url = "http://wallet-service:8099")
public interface CurrencyFeignClientService {

  @GetMapping("/api/currencies/id/{id}")
  public CurrencyResponse getCurrencyById(@PathVariable("id") String id);
}
