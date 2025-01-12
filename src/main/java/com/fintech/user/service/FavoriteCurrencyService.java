package com.fintech.user.service;

import com.fintech.user.config.exception.FavoriteCurrencyNotFoundInUserService;
import com.fintech.user.config.exception.UserNotFoundException;
import com.fintech.user.entity.FavoriteCurrencies;
import com.fintech.user.entity.User;
import com.fintech.user.repository.FavoriteCurrenciesRepository;
import com.fintech.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class FavoriteCurrencyService {

  private final UserRepository userRepository;
  private final FavoriteCurrenciesRepository favoriteCurrenciesRepository;

  public FavoriteCurrencyService(
    UserRepository userRepository,
    FavoriteCurrenciesRepository favoriteCurrenciesRepository
  )
  {
    this.userRepository = userRepository;
    this.favoriteCurrenciesRepository = favoriteCurrenciesRepository;
  }

  /**
   * Add a currency to the user's favorite currencies list.
   *
   * @param userId     the ID of the user
   * @param currencyId the ID of the currency
   * @return the added favorite currency
   */
  @Transactional
  public FavoriteCurrencies addFavoriteCurrency(Long userId, Long currencyId) {
    // Validate user
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));

    // Validate currency by checking with the wallet service
    //CurrencyRequest currencyDTO = walletService.getCurrencyById(currencyId);
//    if (currencyDTO == null) {
//      throw new RuntimeException("Currency not found in Wallet Service");
//    }
//
//    // Check if the currency is already in the user's favorites
//    Optional<FavoriteCurrencies> existingFavorite = favoriteCurrenciesRepository
//      .findByUserIdAndCurrencyId(userId, currencyId);
//
//    if (existingFavorite.isPresent()) {
//      throw new RuntimeException("Currency already in user's favorite list");
//    }

    // Add the currency to the user's favorite list
    FavoriteCurrencies favoriteCurrency = FavoriteCurrencies.builder()
      .user(user)
      .currencyId(currencyId)
      .build();

    user.getFavoriteCurrencies().add(favoriteCurrency);

    userRepository.save(user); // Save user to persist the changes
    return favoriteCurrency;
  }

  /**
   * Remove a currency from the user's favorite currencies list.
   *
   * @param userId     the ID of the user
   * @param currencyId the ID of the currency
   */
  @Transactional
  public void removeFavoriteCurrency(Long userId, Long currencyId) {
    FavoriteCurrencies favoriteCurrency = favoriteCurrenciesRepository.findFavoriteCurrenciesByUserIdAndCurrencyId(userId, currencyId);
    if (favoriteCurrency != null) {
      throw new FavoriteCurrencyNotFoundInUserService(String.valueOf(userId) , String.valueOf(currencyId));
    }
    favoriteCurrenciesRepository.delete(favoriteCurrency);
  }

  /**
   * Get the user's favorite currencies.
   *
   * @param userId the ID of the user
   * @return the set of favorite currencies
   */
  public Set<FavoriteCurrencies> getUserFavoriteCurrencies(Long userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("User not found"));

    return user.getFavoriteCurrencies();
  }
}
