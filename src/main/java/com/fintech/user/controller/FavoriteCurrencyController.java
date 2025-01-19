package com.fintech.user.controller;

import com.fintech.user.dto.responses.CurrencyResponse;
import com.fintech.user.dto.responses.MessageResponse;
import com.fintech.user.entity.FavoriteCurrencies;
import com.fintech.user.service.FavoriteCurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/users/{userId}/favorite-currencies")
public class FavoriteCurrencyController {
  private final FavoriteCurrencyService favoriteCurrencyService;

  public FavoriteCurrencyController(FavoriteCurrencyService favoriteCurrencyService) {
    this.favoriteCurrencyService = favoriteCurrencyService;
  }

  @PostMapping("/{currencyId}")
  public ResponseEntity<CurrencyResponse> addFavoriteCurrency(
    @PathVariable Long userId,
    @PathVariable Long currencyId) {
    CurrencyResponse favoriteCurrency = favoriteCurrencyService.addFavoriteCurrency(userId, currencyId);
    return ResponseEntity.ok(favoriteCurrency);
  }

  @DeleteMapping("/{currencyId}")
  public ResponseEntity<MessageResponse> removeFavoriteCurrency(
    @PathVariable Long userId,
    @PathVariable Long currencyId) {
    favoriteCurrencyService.removeFavoriteCurrency(userId, currencyId);
    return ResponseEntity.ok(
      MessageResponse.builder().message("Currency removed successfully from user").build());
  }

  @GetMapping
  public ResponseEntity<Set<FavoriteCurrencies>> getUserFavoriteCurrencies(
    @PathVariable Long userId) {
    Set<FavoriteCurrencies> favorites = favoriteCurrencyService.getUserFavoriteCurrencies(userId);
    return ResponseEntity.ok(favorites);
  }
}
