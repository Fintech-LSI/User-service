package com.fintech.user.repository;

import com.fintech.user.entity.FavoriteCurrencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteCurrenciesRepository extends JpaRepository<FavoriteCurrencies, Long> {

  /**
   * Custom query to get favorite currencies based on userId and currencyId.
   */
  FavoriteCurrencies findFavoriteCurrenciesByUserIdAndCurrencyId(@Param("userId") Long userId, @Param("currencyId") Long currencyId);

}
