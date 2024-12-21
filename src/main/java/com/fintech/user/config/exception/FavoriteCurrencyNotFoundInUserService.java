package com.fintech.user.config.exception;

public class FavoriteCurrencyNotFoundInUserService extends RuntimeException {
    public FavoriteCurrencyNotFoundInUserService(String user, String currency) {
        super("Could not find Favorite currency " + currency + " in user " + user);
    }
    public FavoriteCurrencyNotFoundInUserService(String currency) {
        super("Could not find Favorite currency " + currency + " in user ");
    }
}
