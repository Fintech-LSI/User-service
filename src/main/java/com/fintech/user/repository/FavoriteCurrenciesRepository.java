package com.fintech.user.repository;

import com.fintech.user.entity.FavoriteCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface FavoriteCurrenciesRepository extends JpaRepository<FavoriteCurrencies, Long> {

  /**
   * Custom query to get favorite currencies based on userId and currencyId.
   */
  @Query("SELECT f FROM FavoriteCurrencies f WHERE f.user = :userId AND f.currencyId = :currencyId")
  FavoriteCurrencies findFavoriteCurrenciesByUserIdAndCurrencyId(@Param("userId") Long userId, @Param("currencyId") Long currencyId);

}
