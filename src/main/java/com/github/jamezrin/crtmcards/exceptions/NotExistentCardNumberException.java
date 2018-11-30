package com.github.jamezrin.crtmcards.exceptions;

public class NotExistentCardNumberException extends InvalidCardNumberException {
    public NotExistentCardNumberException(String message) {
        super(message);
    }
}
