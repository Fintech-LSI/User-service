package com.fintech.user.service;

import com.fintech.user.config.exception.FavoriteCurrencyNotFoundInUserService;
import com.fintech.user.config.exception.UserNotFoundException;
import com.fintech.user.dto.responses.CurrencyResponse;
import com.fintech.user.entity.FavoriteCurrencies;
import com.fintech.user.entity.User;
import com.fintech.user.repository.FavoriteCurrenciesRepository;
import com.fintech.user.repository.UserRepository;
import com.fintech.user.service.feign_clients.CurrencyFeignClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class FavoriteCurrencyService {

  private final UserRepository userRepository;
  private final FavoriteCurrenciesRepository favoriteCurrenciesRepository;
  private final CurrencyFeignClientService currencyFeignClientService;

  public FavoriteCurrencyService(
    UserRepository userRepository,
    FavoriteCurrenciesRepository favoriteCurrenciesRepository,
    CurrencyFeignClientService currencyFeignClientService
  ) {
    this.userRepository = userRepository;
    this.favoriteCurrenciesRepository = favoriteCurrenciesRepository;
    this.currencyFeignClientService = currencyFeignClientService;
  }

    @Transactional
    public CurrencyResponse addFavoriteCurrency(Long userId, Long currencyId) {
      User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));

      // Check if the currency is already in favorites
      if (favoriteCurrenciesRepository.findFavoriteCurrenciesByUserIdAndCurrencyId(userId, currencyId) != null) {
        throw new IllegalStateException("Currency is already in favorites");
      }

      FavoriteCurrencies favoriteCurrency = FavoriteCurrencies.builder()
        .user(user)
        .currencyId(currencyId)
        .build();

      user.getFavoriteCurrencies().add(favoriteCurrency);
      userRepository.save(user);
      CurrencyResponse favoriteCurrencyResponse = currencyFeignClientService.
          getCurrencyById(String.valueOf(currencyId));
      return favoriteCurrencyResponse;
    }

  @Transactional
  public void removeFavoriteCurrency(Long userId, Long currencyId) {
    FavoriteCurrencies favoriteCurrency = favoriteCurrenciesRepository
      .findFavoriteCurrenciesByUserIdAndCurrencyId(userId, currencyId);

    if (favoriteCurrency == null) {
      throw new FavoriteCurrencyNotFoundInUserService(String.valueOf(userId), String.valueOf(currencyId));
    }

    User user = favoriteCurrency.getUser();
    user.getFavoriteCurrencies().remove(favoriteCurrency);
    favoriteCurrenciesRepository.delete(favoriteCurrency);
  }

  public Set<FavoriteCurrencies> getUserFavoriteCurrencies(Long userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
    return user.getFavoriteCurrencies();
  }
}
